package io.github.chumper.lottery

import java.io.FileWriter

import org.scalatest.FunSuite

/**
  * Will generate the tickets to work with
  */
class TicketGenerationTest extends FunSuite {

  test("Generate 100 System tickets to file") {

    val fw = new FileWriter("system-tickets.txt", false)

    Ticket.generateSystemTickets(100).foreach { t =>
      fw.write(t.format() + "\r\n")
    }
    fw.close()
  }

  test("Generate 100 Normal tickets to file") {

    val fw = new FileWriter("normal-tickets.txt", false)

    Ticket.generateNormalTickets(100).foreach { t =>
      fw.write(t.format() + "\r\n")
    }
    fw.close()
  }

  test("Generate 100 mixed tickets to file") {

    val fw = new FileWriter("mixed-tickets.txt", false)

    (Ticket.generateSystemTickets(100) ++ Ticket.generateNormalTickets(50)).foreach { t =>
      fw.write(t.format() + "\r\n")
    }
    fw.close()
  }

  test("Generate 1 winning ticket to file") {

    val fw = new FileWriter("winning-ticket.txt", false)

    Ticket.generateNormalTickets(1).foreach { t =>
      fw.write(t.format() + "\r\n")
    }
    fw.close()
  }

}
