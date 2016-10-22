package io.github.chumper.lottery

import java.util.concurrent.TimeUnit

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{FiniteDuration, _}
import scala.concurrent.{Await, Future}
import scala.io.Source

/**
  * This app will just read a bunch of system tickets from a file and outputs all possible combinations of those
  */
object ReadSystemTickets extends App {

  val filename = "system-tickets.txt"
  for (line <- Source.fromFile(filename).getLines()) {
    val ticket = Ticket.parse(line)
    // get combinations
    val combos = ticket.combinations
    // print ticket
    println(ticket.format())
    // print combinations
    combos.foreach { t => println("|-- " + t.format()) }
  }
}

object SimulateLottery extends App {

  // read the winning ticket
  val winningTicket = Ticket.parse(Source.fromFile("winning-ticket.txt").mkString.replace("\r\n", ""))

  // create a new lottery from it
  val lottery = new Lottery(winningTicket)

  // a list of futures we will wait on before ending the programm
  var futures: ListBuffer[Future[Unit]] = ListBuffer()

  // calculate the time needed
  val start = System.nanoTime()

  // read participating tickets
  for (line <- Source.fromFile("mixed-tickets.txt").getLines()) {
    val ticket = Ticket.parse(line)
    // evaluate and pass future to list
    futures += lottery.evaluate(ticket)
  }

  // wait until all tickets have been evaluated or 60 seconds max
  Await.result(Future.sequence(futures), 60.seconds)

  // calculate the time the lottery took
  val end = FiniteDuration(System.nanoTime() - start, TimeUnit.NANOSECONDS).toMillis

  println(s"Calculation took $end ms.")

  // print winning classes
  lottery.printResult
}
