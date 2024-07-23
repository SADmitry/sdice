package sdmitry

import sdmitry.systems.GamingSystemWithNegation
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
    * @return List of gaming statistics
    */
    def faceToFaceNegating[G <: GamingSystemWithNegation[R, D]](system: G): List[String] =
        system.explain(outcomes.map ( outcome => system.negation(outcome) ))

    override def equals(that: Any): Boolean =
        that match
            case o: Outcomes[_, _] =>
                this.outcomes == o.outcomes
            case _ =>
                false

class Res[R, D <: Dice[R]](val res: R, val d: D):
    def explain(): String = res.toString()

    override def toString(): String = s"Res($res)"

    override def equals(that: Any): Boolean =
        that match
            case r: Res[_, _] =>
                this.res == r.res
            case _ =>
                false