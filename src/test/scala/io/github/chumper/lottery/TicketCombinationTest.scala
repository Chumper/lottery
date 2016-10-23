package io.github.chumper.lottery

import org.scalatest.{FunSuite, Matchers}

/**
  * Will test if the ticket returns the correct amount and configuration of combinations
  */
class TicketCombinationTest extends FunSuite with Matchers {

  test("A Ticket yields only one combination") {

    val t = new Ticket(Set(1, 2, 3, 4, 5), Set(1, 2))

    t.combinations should have size 1
    assert(t.combinations.exists(_.starNumbers == Set(1, 2)))
  }

  test("A Ticket yields only three combination") {
    val t = new Ticket(Set(1, 2, 3, 4, 5), Set(1, 2, 3))

    t.combinations should have size 3

    assert(t.combinations.exists(_.starNumbers == Set(1, 2)))
    assert(t.combinations.exists(_.starNumbers == Set(1, 3)))
    assert(t.combinations.exists(_.starNumbers == Set(2, 3)))
  }

  test("A Ticket yields only six combination") {
    val t = new Ticket(Set(1, 2, 3, 4, 5, 6), Set(1, 2))

    t.combinations should have size 6

    assert(t.combinations.exists(_.normalNumbers == Set(1, 2, 3, 4, 5)))
    assert(t.combinations.exists(_.normalNumbers == Set(1, 2, 3, 4, 6)))
    assert(t.combinations.exists(_.normalNumbers == Set(1, 2, 3, 6, 5)))
    assert(t.combinations.exists(_.normalNumbers == Set(1, 2, 6, 4, 5)))
    assert(t.combinations.exists(_.normalNumbers == Set(1, 6, 3, 4, 5)))
    assert(t.combinations.exists(_.normalNumbers == Set(6, 2, 3, 4, 5)))
  }

}
