package io.github.chumper.lottery

import org.scalatest.{AsyncFunSuite, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Will test if the ticket returns the correct amount and configuration of combinations
  */
class TicketCombinationTest extends AsyncFunSuite with Matchers {

  test("A Ticket yields only one combination") {

    val tickets = Ticket.generateSystemTickets(1)

    val t = new Ticket(Set(1, 2, 3, 4, 5), Set(1, 2))

    t.combinations map { combinations =>
      combinations should have size 1
      assert(combinations.exists(_._2 == Set(1, 2)))
    }
  }

  test("A Ticket yields only three combination") {
    val t = new Ticket(Set(1, 2, 3, 4, 5), Set(1, 2, 3))

    t.combinations map { combinations =>
      combinations should have size 3

      assert(combinations.exists(_._2 == Set(1, 2)))
      assert(combinations.exists(_._2 == Set(1, 3)))
      assert(combinations.exists(_._2 == Set(2, 3)))
    }
  }

  test("A Ticket yields only six combination") {
    val t = new Ticket(Set(1, 2, 3, 4, 5, 6), Set(1, 2))

    t.combinations map { combinations =>
      combinations should have size 6

      assert(combinations.exists(_._1 == Set(1, 2, 3, 4, 5)))
      assert(combinations.exists(_._1 == Set(1, 2, 3, 4, 6)))
      assert(combinations.exists(_._1 == Set(1, 2, 3, 6, 5)))
      assert(combinations.exists(_._1 == Set(1, 2, 6, 4, 5)))
      assert(combinations.exists(_._1 == Set(1, 6, 3, 4, 5)))
      assert(combinations.exists(_._1 == Set(6, 2, 3, 4, 5)))
    }
  }
}
