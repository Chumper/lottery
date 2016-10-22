package io.github.chumper.lottery

import org.scalatest.FunSuite

/**
  * Will test if a ticket can be constructed wit valid numbers and fails on invalid numbers
  */
class TicketTest extends FunSuite {

  test("A Ticket can have only valid numbers") {
    new Ticket(Set(1, 2, 3, 4, 5, 50), Set(1, 2))
  }
  test("A Ticket can not have negative normal numbers") {
    assertThrows[IllegalArgumentException] {
      new Ticket(Set(0, 1, 2, 3, 4, 5), Set(1, 2))
    }
  }
  test("A Ticket can not have negative star numbers") {
    assertThrows[IllegalArgumentException] {
      new Ticket(Set(0, 1, 2, 3, 4, 5), Set(0, 2))
    }
  }
  test("A Ticket can not have out of range numbers") {
    assertThrows[IllegalArgumentException] {
      new Ticket(Set(51, 1, 2, 3, 4, 5), Set(1, 2))
    }
  }
  test("A Ticket can have not have negative star numbers") {
    assertThrows[IllegalArgumentException] {
      new Ticket(Set(51, 1, 2, 3, 4, 5), Set(0, 1))
    }
  }
  test("A Ticket can have not have out of range star numbers") {
    assertThrows[IllegalArgumentException] {
      new Ticket(Set(51, 1, 2, 3, 4, 5), Set(13, 12))
    }
  }
  test("A Ticket can have not have duplicated numbers") {
      new Ticket(Set(1, 1, 2, 3, 4, 5), Set(6, 6, 12))
  }
  test("A Ticket needs at least 5 normal numbers") {
    assertThrows[IllegalArgumentException] {
      new Ticket(Set(2, 3), Set(6, 12))
    }
  }
  test("A Ticket needs at least 2 star numbers") {
    assertThrows[IllegalArgumentException] {
      new Ticket(Set(1, 2, 3, 4, 5), Set(6))
    }
  }
}
