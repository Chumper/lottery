package io.github.chumper.lottery

import com.typesafe.config.ConfigFactory

import scala.util.Random

/**
  * Represents a lottery ticket which can be a normal or a system ticket
  */
class Ticket(val normalNumbers: Set[Int], val starNumbers: Set[Int]) {

  // check all values if they are in the correct range
  require(normalNumbers forall (i => i >= Ticket.MinNormalNumber && i <= Ticket.MaxNormalNumber), "Normal numbers contains invalid numbers")
  require(starNumbers forall (i => i >= Ticket.MinStarNumber && i <= Ticket.MaxStarNumber), "Star numbers contains invalid numbers")
  require(normalNumbers.size >= Ticket.MinNormalSystemNumbers, s"At least ${Ticket.MinNormalSystemNumbers} normal numbers are needed")
  require(starNumbers.size >= Ticket.MinStarSystemNumbers, s"At least ${Ticket.MinStarSystemNumbers} star numbers are needed")
  require(normalNumbers.size <= Ticket.MaxNormalSystemNumbers, s"At most ${Ticket.MaxNormalSystemNumbers} normal numbers are needed")
  require(starNumbers.size <= Ticket.MaxStarSystemNumbers, s"At most ${Ticket.MaxStarSystemNumbers} star numbers are needed")

  /**
    * Will permutate all possible plain tickets and returns them as a sequence of tuples containing 5 normal numbers and
    * 2 star numbers
    *
    * @return A future containing a sequence of tuples
    */
  def combinations: Seq[Ticket] = {
    // just use scala built in features, no need to do this on our own...
    for (
      n <- normalNumbers.toSeq.combinations(5).toSeq;
      s <- starNumbers.toSeq.combinations(2).toSeq
    ) yield new Ticket(n.toSet, s.toSet)
  }

  /**
    * Will return a string representation of this ticket
    * A ticket will then look like this: 1,2,3,4,5:1,2
    *
    * @return String the text representation of this ticket
    */
  def format(): String = {
    normalNumbers.mkString(",") + ":" + starNumbers.mkString(",")
  }

  override def toString: String = format()
}

object Ticket {

  // CONSTANTS
  val config = ConfigFactory.load()

  val MaxNormalNumber = config.getInt("lottery.number.normal.max")
  val MinNormalNumber = config.getInt("lottery.number.normal.min")
  val MaxStarNumber = config.getInt("lottery.number.star.max")
  val MinStarNumber = config.getInt("lottery.number.star.min")
  val MaxNormalSystemNumbers = config.getInt("lottery.ticket.numbers.normal.max")
  val MinNormalSystemNumbers = config.getInt("lottery.ticket.numbers.normal.min")
  val MaxStarSystemNumbers = config.getInt("lottery.ticket.numbers.star.max")
  val MinStarSystemNumbers = config.getInt("lottery.ticket.numbers.star.min")

  /**
    * Parse a ticket from a string representation (e.g. 1,2,3,4,5:1,2) to a valid ticket
    *
    * @param value The text representation of the string
    */
  def parse(value: String): Ticket = {
    val split = value.split(":", 2)
    new Ticket(split(0).split(",").map(_.toInt).toSet, split(1).split(",").map(_.toInt).toSet[Int])
  }

  /**
    * Will generate the given amount of system tickets
    *
    * @param amount the number of tickets to generate
    */
  def generateTickets(amount: Int): Seq[Ticket] = {
    val additionalNormalNumbers = (MaxNormalSystemNumbers - MinNormalSystemNumbers) + 1
    val additionalStarNumbers = (MaxStarSystemNumbers - MinStarSystemNumbers) + 1

    1 to amount map { _ =>
      val systemNumbers = Random.shuffle(MinNormalNumber to MaxNormalNumber).toList.take(MinNormalSystemNumbers + Random.nextInt(additionalNormalNumbers))
      val starNumbers = Random.shuffle(MinStarNumber to MaxStarNumber).toList.take(MinStarSystemNumbers + Random.nextInt(additionalStarNumbers))

      new Ticket(systemNumbers.toSet, starNumbers.toSet)
    }
  }
}
