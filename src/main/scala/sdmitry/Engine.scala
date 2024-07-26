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

class Outcomes[R, D <: Dice[R]](val outcomes: Iterable[Iterable[Res[R, D]]]):
    /**
    * Calculate statistics from given pool for given type of system
    * @return resolved roll 
    */
    def resolveNegating[G <: GamingSystemWithNegation[R, D]](system: G): Iterable[Iterable[Res[R, D]]] =
        outcomes.map (outcome => system.negation(outcome))

    def resolveNegatingRange[G <: GamingSystemWithNegationInRange[R, D]](
        system: G,
        firstRange: (Res[R, D]) => Boolean,
        secondRange: (Res[R, D]) => Boolean
    ): Iterable[Iterable[Res[R, D]]] =
        outcomes.map (outcome => system.negation(outcome, firstRange, secondRange))

    def explain[G <: GamingSystem[R, D]](system: G, resolved: Iterable[Iterable[Res[R, D]]]): List[String] =
        system.explain(resolved)

    override def equals(that: Any): Boolean =
        that match
            case o: Outcomes[_, _] =>
                this.outcomes == o.outcomes
            case _ =>
                false

class Res[+R, +D <: Dice[R]](val res: R, val d: D):
    override def toString(): String = s"Res($res)"

    override def equals(that: Any): Boolean =
        that match
            case r: Res[_, _] =>
                this.res == r.res
            case _ =>
                false