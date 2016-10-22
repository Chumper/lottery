package io.github.chumper.lottery

import scala.util.Random

/**
  * Represents a lottery ticket which can be a normal or a system ticket
  */
class Ticket(val normalNumbers: Set[Int], val starNumbers: Set[Int]) {

  // check all values if they are in the correct range
  require(normalNumbers forall (i => i > 0 && i <= Ticket.MaxNormalNumber), "Normal numbers contains invalid numbers")
  require(starNumbers forall (i => i >= 0 && i <= Ticket.MaxStarNumber), "Star numbers contains invalid numbers")
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
  val MaxNormalNumber = 50
  val MaxStarNumber = 12
  val MaxNormalSystemNumbers = 10
  val MaxStarSystemNumbers = 5
  val MinNormalSystemNumbers = 5
  val MinStarSystemNumbers = 2

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
  def generateSystemTickets(amount: Int): Seq[Ticket] = {
    val additionalNormalNumbers = (MaxNormalSystemNumbers - MinNormalSystemNumbers) + 1
    val additionalStarNumbers = (MaxStarSystemNumbers - MinStarSystemNumbers) + 1

    1 to amount map { _ =>
      val systemNumbers = Random.shuffle(1 to MaxNormalNumber).toList.take(MinNormalSystemNumbers + Random.nextInt(additionalNormalNumbers))
      val starNumbers = Random.shuffle(1 to MaxStarNumber).toList.take(MinStarSystemNumbers + Random.nextInt(additionalStarNumbers))

      new Ticket(systemNumbers.toSet, starNumbers.toSet)
    }
  }

  /**
    * Will generate the given amount of normal tickets
    *
    * @param amount the number of tickets to generate
    */
  def generateNormalTickets(amount: Int): Seq[Ticket] = {
    1 to amount map { _ =>
      val systemNumbers = Random.shuffle(1 to MaxNormalNumber).toList.take(MinNormalSystemNumbers)
      val starNumbers = Random.shuffle(1 to MaxStarNumber).toList.take(MinStarSystemNumbers)

      new Ticket(systemNumbers.toSet, starNumbers.toSet)
    }
  }
}
