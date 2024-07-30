package sdmitry.systems

import sdmitry.Dice
import sdmitry.Res
import sdmitry.Dice

trait GamingSystem[R, D <: Dice[R]]:
    /**
      * @param stats is a map of tags and respective count of elements
      * @return explained statistics depending on a gaming system
      */
    def explain(stats: Map[String, Long]): Seq[String]

    /**
      * @param roll possible roll
      * @return case is classified, tags are returned
      */
    def classify(roll: Iterable[Res[R, D]]): Seq[String]

trait GamingSystemWithNegationInRange[R, D <: Dice[R]] extends GamingSystem[R, D]:
    /**
      * Acceptable range for dice of the first player
      */
    def firstRange: (Res[R, D]) => Boolean

    /**
      * Acceptable range for dice of the second player
      */
    def secondRange: (Res[R, D]) => Boolean
    