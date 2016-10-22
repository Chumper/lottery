package io.github.chumper.lottery

import java.util.concurrent.atomic.AtomicInteger

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Lottery(winningTicket: Ticket) {

  val statistic: mutable.Map[Int, AtomicInteger] = mutable.Map(Lottery.winningClasses.map(wc => wc._1 -> new AtomicInteger(0)): _*)

  def printResult = {
    statistic.keySet.toSeq.sorted.foreach { i => println(s"Winning class $i has ${statistic(i)} winner!")}
    println("Total number of tickets: " + statistic.values.map(_.get()).sum)
  }

  def evaluate(ticket: Ticket): Future[Unit] = {
    Future {
      ticket.combinations.foreach { combo =>
        val matchingNormalNumbers = winningTicket.normalNumbers.intersect(combo.normalNumbers).size
        val matchingStarNumbers = winningTicket.starNumbers.intersect(combo.starNumbers).size

        val won = Lottery.winningClasses.find { wc => wc._2 == matchingNormalNumbers && wc._3 == matchingStarNumbers }

        won match {
          case Some(t) => statistic(t._1).incrementAndGet()
          case None =>
        }
      }
    }
  }
}

object Lottery {
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
    (13, 2, 0),
    // added ones
    (14, 1, 0),
    (15, 1, 1),
    (16, 0, 2),
    (17, 0, 1),
    (18, 0, 0)
  )
}
