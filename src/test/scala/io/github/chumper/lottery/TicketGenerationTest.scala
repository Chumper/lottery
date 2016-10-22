package io.github.chumper.lottery

import java.io.FileWriter

import org.scalatest.FunSuite

/**
  * Created by n.plaschke on 22/10/16.
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

}
