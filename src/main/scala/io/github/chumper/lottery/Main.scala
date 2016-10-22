package io.github.chumper.lottery

import java.util.concurrent.{Executors, TimeUnit}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.{FiniteDuration, _}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.io.Source

object ReadSystemTickets extends App {

  val filename = "system-tickets.txt"
  for (line <- Source.fromFile(filename).getLines()) {
    val ticket = Ticket.parse(line)
    val combos = ticket.combinations
    println(ticket.format())
    combos.foreach { t => println("|-- " + t.format()) }
  }
}

object SimulateLottery extends App {

  val winningTicket = Ticket.parse(Source.fromFile("winning-ticket.txt").mkString.replace("\r\n", ""))

  val lottery = new Lottery(winningTicket)

  var futures: ListBuffer[Future[Unit]] = ListBuffer()

  val start = System.nanoTime()
  for (line <- Source.fromFile("mixed-tickets.txt").getLines()) {
    val ticket = Ticket.parse(line)
    futures += lottery.evaluate(ticket)
  }

  Await.result(Future.sequence(futures), 60.seconds)

  val end = FiniteDuration(System.nanoTime() - start, TimeUnit.NANOSECONDS).toMillis

  println(s"Calculation took $end ms.")
  lottery.printResult
}
