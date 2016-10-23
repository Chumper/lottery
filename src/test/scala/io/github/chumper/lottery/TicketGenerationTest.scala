package io.github.chumper.lottery

import java.io.FileWriter

import org.scalatest.FunSuite

/**
  * Will generate the tickets to work with, not a real test but prepares the application
  */
class TicketGenerationTest extends FunSuite {

  test("Generate 10000 mixed tickets to file") {

    val fw = new FileWriter("tickets.txt", false)

    Ticket.generateTickets(10000).foreach { t =>
      fw.write(t.format() + "\r\n")
    }
    fw.close()
  }

  test("Generate 1 winning ticket to file") {

    val fw = new FileWriter("winning-ticket.txt", false)

    Ticket.generateTickets(1).foreach { t =>
      fw.write(t.format() + "\r\n")
    }
    fw.close()
  }

}
