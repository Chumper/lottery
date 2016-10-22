package io.github.chumper.lottery

import org.scalatest.{AsyncFunSuite, Matchers}

/**
  * Will test if the lottery evaluates the tickets correctly
  */
class LotteryTest extends AsyncFunSuite with Matchers {

  test("The lottery evaluates the correct ticket: 1") {
    val l = new Lottery(Ticket.parse("1,2,3,4,5:1,2"))

    val f = l.evaluate(Ticket.parse("1,2,3,4,5:1,2"))

    f map { _ => l.statistic(1).get() shouldBe 1 }
  }

  test("The lottery evaluates the correct ticket: 2") {
    val l = new Lottery(Ticket.parse("1,2,3,4,5:1,2"))

    val f = l.evaluate(Ticket.parse("1,2,3,4,5:1,3"))

    f map { _ => l.statistic(2).get() shouldBe 1 }
  }

  test("The lottery evaluates the correct ticket: 3") {
    val l = new Lottery(Ticket.parse("1,2,3,4,5:1,2"))

    val f = l.evaluate(Ticket.parse("1,2,3,4,5:3,4"))

    f map { _ => l.statistic(3).get() shouldBe 1 }
  }

  test("The lottery evaluates the correct ticket: 4") {
    val l = new Lottery(Ticket.parse("1,2,3,4,5:1,2"))

    val f = l.evaluate(Ticket.parse("1,2,3,4,6:1,2"))

    f map { _ => l.statistic(4).get() shouldBe 1 }
  }

  test("The lottery evaluates the correct ticket: 5") {
    val l = new Lottery(Ticket.parse("1,2,3,4,5:1,2"))

    val f = l.evaluate(Ticket.parse("1,2,3,4,6:1,4"))

    f map { _ => l.statistic(5).get() shouldBe 1 }
  }

  test("The lottery evaluates the correct ticket: 6") {
    val l = new Lottery(Ticket.parse("1,2,3,4,5:1,2"))

    val f = l.evaluate(Ticket.parse("1,2,3,4,6:3,4"))

    f map { _ => l.statistic(6).get() shouldBe 1 }
  }

  test("The lottery evaluates the correct ticket: 7") {
    val l = new Lottery(Ticket.parse("1,2,3,4,5:1,2"))

    val f = l.evaluate(Ticket.parse("1,2,3,6,7:1,2"))

    f map { _ => l.statistic(7).get() shouldBe 1 }
  }

  test("The lottery evaluates the correct ticket: 8") {
    val l = new Lottery(Ticket.parse("1,2,3,4,5:1,2"))

    val f = l.evaluate(Ticket.parse("1,2,8,6,7:1,2"))

    f map { _ => l.statistic(8).get() shouldBe 1 }
  }

  test("The lottery evaluates the correct ticket: 9") {
    val l = new Lottery(Ticket.parse("1,2,3,4,5:1,2"))

    val f = l.evaluate(Ticket.parse("1,2,3,6,7:1,3"))

    f map { _ => l.statistic(9).get() shouldBe 1 }
  }

  test("The lottery evaluates the correct ticket: 10") {
    val l = new Lottery(Ticket.parse("1,2,3,4,5:1,2"))

    val f = l.evaluate(Ticket.parse("1,2,3,6,7:3,4"))

    f map { _ => l.statistic(10).get() shouldBe 1 }
  }

  test("The lottery evaluates the correct ticket: 11") {
    val l = new Lottery(Ticket.parse("1,2,3,4,5:1,2"))

    val f = l.evaluate(Ticket.parse("1,9,8,6,7:1,2"))

    f map { _ => l.statistic(11).get() shouldBe 1 }
  }

  test("The lottery evaluates the correct ticket: 12") {
    val l = new Lottery(Ticket.parse("1,2,3,4,5:1,2"))

    val f = l.evaluate(Ticket.parse("1,2,8,6,7:1,3"))

    f map { _ => l.statistic(12).get() shouldBe 1 }
  }

  test("The lottery evaluates the correct ticket: 13") {
    val l = new Lottery(Ticket.parse("1,2,3,4,5:1,2"))

    val f = l.evaluate(Ticket.parse("1,2,8,6,7:3,4"))

    f map { _ => l.statistic(13).get() shouldBe 1 }
  }
}
