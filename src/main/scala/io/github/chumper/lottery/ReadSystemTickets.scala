package io.github.chumper.lottery

import scala.io.Source

/**
  * Created by n.plaschke on 22/10/16.
  */
class ReadSystemTickets extends App{

  val filename = "system-tickets.txt"
  for (line <- Source.fromFile(filename).getLines()) {
    val ticket = Ticket.parse(line)
    ticket.combinations.map { cs =>
      println(ticket.format())
      cs.foreach { t => println("|--" + t._1.toString() + ":" + t._2.toString() )}
    }
  }
}
