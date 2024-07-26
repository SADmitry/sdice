package sdmitry

import sdmitry.systems.GamingSystem
import sdmitry.systems.GamingSystemWithNegation
import sdmitry.systems.GamingSystemWithNegationInRange
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Buffer

class DiceEngine[R, D <: Dice[R]](val pool: Iterable[D]):
    /**
      * Calculate all possible outcomes from given dice pool
      * @return List of possible outcomes represented by list of dice results
      */
    def outcomes(): Outcomes[R, D] =
        val initial = ArrayBuffer(ArrayBuffer.empty[Res[R, D]])
        val combinations = pool.map(d => d.possibleOutcomes().map(o => Res[R, D](o, d)))
            .foldRight(initial)((currentList, acc) =>
            val newAcc = ArrayBuffer[ArrayBuffer[Res[R, D]]]()
            for {
                elem <- currentList
                rest <- acc
            } yield {
                newAcc += (elem +: rest)
            }
            newAcc
        )
        Outcomes(combinations)

case class Outcomes[R, D <: Dice[R]](val outcomes: Iterable[Iterable[Res[R, D]]]):
    /**
    * Calculate statistics from given pool for given type of system
    * @return resolved roll 
    */
    def resolveNegating[G <: GamingSystemWithNegation[R, D]](system: G): Resolved[R, D] =
        val negated = outcomes.map (outcome => system.negation(outcome))
        Resolved(negated)

    def resolveNegatingRange[G <: GamingSystemWithNegationInRange[R, D]](
        system: G,
        firstRange: (Res[R, D]) => Boolean,
        secondRange: (Res[R, D]) => Boolean
    ): Resolved[R, D] =
        val negated = outcomes.map (outcome => system.negation(outcome, firstRange, secondRange))
        Resolved(negated)

case class Resolved[R, D <: Dice[R]](val outcomes: Iterable[Iterable[Res[R, D]]]):
    def explain[G <: GamingSystem[R, D]](system: G): List[String] =
        system.explain(outcomes)
