package io.github.chumper.lottery

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
