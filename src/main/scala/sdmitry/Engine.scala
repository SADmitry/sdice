package sdmitry

import sdmitry.systems.GamingSystemWithNegation
import scala.collection.mutable.ArrayBuffer

class DiceEngine(val pool: List[D]):
    /**
      * Calculate all possible outcomes from given dice pool
      * @return List of possible outcomes represented by list of dice results
      */
    def outcomes(): Outcomes =
        Outcomes(
            pool.map {d => d.possibleOutcomes()}
                .foldRight(ArrayBuffer(ArrayBuffer.empty[Res])) { (currentList, acc) =>
                    val newAcc = ArrayBuffer[ArrayBuffer[Res]]()
                    for {
                        elem <- currentList
                        rest <- acc
                    } yield {
                        newAcc += (elem +: rest)
                    }
                    newAcc
                }
        )

class Outcomes(val outcomes: Iterable[Iterable[Res]]):
    /**
    * Calculate statistics from given pool for given type of system
    * @return List of gaming statistics
    */
    def faceToFaceNegating[T <: GamingSystemWithNegation](system: T): List[String] =
        system.explain(outcomes.map ( outcome => system.negation(outcome) ))

    override def equals(that: Any): Boolean =
        that match
            case o: Outcomes =>
                this.outcomes == o.outcomes
            case _ =>
                false
