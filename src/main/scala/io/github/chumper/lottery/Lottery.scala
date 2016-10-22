package io.github.chumper.lottery

import java.util.concurrent.atomic.AtomicInteger

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * The Lottery is revaluating all tickets and updates the statistics about the winners
  * @param winningTicket The ticket with the winning numbers
  */
class Lottery(winningTicket: Ticket) {

  /**
    * A map with the winning class and an atomic integer to count the number of winners.
    * As this map will potentially be accessed from multiple Threads it is a good idea to use an atomic type
    */
  val statistic: mutable.Map[Int, AtomicInteger] = mutable.Map(Lottery.winningClasses.map(wc => wc._1 -> new AtomicInteger(0)): _*)

  /**
    * The number of total tickets that have been evaluated, not really needed, just for statistics.
    */
  val totalTickets = new AtomicInteger(0)

  /**
    * Will print the winning classes with the amount of tickets that fit into this class, will also output
    * the total number of participated tickets.
    */
  def printResult = {
    statistic.keySet.toSeq.sorted.foreach { i => println(s"Winning class $i has ${statistic(i)} winner!")}
    println("Total number of tickets: " + totalTickets.get)
  }

  /**
    * The evaluate method will take all combinations of the ticket and match it against the winning classes.
    * If a ticket fits into a winning class, then the statistics will be updated.
    * @param ticket The ticket to evaluate
    * @return Future a future so we can check many tickets concurrently
    */
  def evaluate(ticket: Ticket): Future[Unit] = {
    Future {
      ticket.combinations.foreach { combo =>

        totalTickets.incrementAndGet

        // determine the amount of matching normal and star numbers
        val matchingNormalNumbers = winningTicket.normalNumbers.intersect(combo.normalNumbers).size
        val matchingStarNumbers = winningTicket.starNumbers.intersect(combo.starNumbers).size

        // find the correct winning class
        val won = Lottery.winningClasses.find { wc => wc._2 == matchingNormalNumbers && wc._3 == matchingStarNumbers }

        // if found, update statistics
        won match {
          case Some(t) => statistic(t._1).incrementAndGet
          case None =>
        }
      }
    }
  }
}

object Lottery {
  /**
    * This Sequence represents the winning classes, the first parameter is the winning class.
    * The second parameter represents the amount of matching normalNumbers needed to fit into this class.
    * The third parameter represents the amount of matching starNumbers needed to fit into this class.
    */
  val winningClasses: Seq[(Int, Int, Int)] = Seq(
    (1, 5, 2),
    (2, 5, 1),
    (3, 5, 0),
    (4, 4, 2),
    (5, 4, 1),
    (6, 4, 0),
    (7, 3, 2),
    (8, 2, 2),
    (9, 3, 1),
    (10, 3, 0),
    (11, 1, 2),
    (12, 2, 1),
    (13, 2, 0)
    // added ones
//    (14, 1, 0),
//    (15, 1, 1),
//    (16, 0, 2),
//    (17, 0, 1),
//    (18, 0, 0)
  )
}
