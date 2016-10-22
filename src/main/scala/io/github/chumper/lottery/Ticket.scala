package io.github.chumper.lottery

import scala.concurrent.Future

/**
  * Represents a lottery ticket which can be a normal or a system ticket
  */
class Ticket(val normalNumbers: Set[Int], val starNumbers: Set[Int]) {

  // check all values if they are in the correct range
  require(normalNumbers forall (i => i > 0 && i <= 50), "Normal numbers contains invalid numbers")
  require(starNumbers forall (i => i >= 0 && i <= 12), "Star numbers contains invalid numbers")
  require(normalNumbers.size >= 5, "At least 5 normal numbers are needed")
  require(starNumbers.size >= 2, "At least 2 normal numbers are needed")

  /**
    * Will permutate all possible plain tickets and returns them as a sequence of tuples containing 5 normal numbers and
    * 2 star numbers
    *
    * @return A sequence of tuples
    */
  def combinations: Future[Seq[(Set[Int], Set[Int])]] = {
    Future {
      for (
        n <- normalNumbers.toSeq.combinations(5).toSeq;
        s <- starNumbers.toSeq.combinations(2).toSeq
      ) yield (n.toSet, s.toSet)
    }
  }
}
