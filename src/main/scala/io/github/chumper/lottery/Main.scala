package io.github.chumper.lottery

import java.io.{File, FileWriter}
import java.util.concurrent.TimeUnit

import com.typesafe.config.ConfigFactory

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{FiniteDuration, _}
import scala.concurrent.{Await, Future}
import scala.io.Source

///**
//  * This app will just read a bunch of system tickets from a file and outputs all possible combinations of those
//  */
//object ReadSystemTickets extends App {
//
//  val filename = "tickets.txt"
//  for (line <- Source.fromFile(filename).getLines()) {
//    val ticket = Ticket.parse(line)
//    // get combinations
//    val combos = ticket.combinations
//    // print ticket
//    println(ticket.format())
//    // print combinations
//    combos.foreach { t => println("|-- " + t.format()) }
//  }
//}

object Main extends App {

  val config = ConfigFactory.load()

  // generate test files if not there yet
  if(!new File(config.getString("lottery.winningTicket")).exists) {
    val fw = new FileWriter(config.getString("lottery.winningTicket"), false)

    Ticket.generateTickets(1).foreach { t =>
      fw.write(t.format() + "\r\n")
    }
    fw.close()
  }

  if(!new File(config.getString("lottery.participatingTickets")).exists) {
    val fw = new FileWriter(config.getString("lottery.participatingTickets"), false)

    Ticket.generateTickets(10000).foreach { t =>
      fw.write(t.format() + "\r\n")
    }
    fw.close()
  }

  // read the winning ticket
  val winningTicket = Ticket.parse(Source.fromFile(config.getString("lottery.winningTicket")).mkString.replace("\r\n", ""))

  // for all combinations we will simulate a lottery
  winningTicket.combinations.foreach { singleTicket =>

    // create a new lottery from it
    val lottery = new Lottery(singleTicket)

    // a list of futures we will wait on before ending the programm
    var futures: ListBuffer[Future[Unit]] = ListBuffer()

    // calculate the time needed
    val start = System.nanoTime()

    // read participating tickets
    for (line <- Source.fromFile(config.getString("lottery.participatingTickets")).getLines()) {
      val ticket = Ticket.parse(line)
      // evaluate and pass future to list
      futures += lottery.evaluate(ticket)
    }

    // wait until all tickets have been evaluated or 60 seconds max
    Await.result(Future.sequence(futures), 60.seconds)

    // calculate the time the lottery took
    val end = FiniteDuration(System.nanoTime() - start, TimeUnit.NANOSECONDS).toMillis

    println("===========================")
    println(s"Calculation took $end ms.")
    // print winning classes
    lottery.printResult
    println("===========================")

  }
}
