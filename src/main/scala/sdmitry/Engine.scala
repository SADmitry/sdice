package sdmitry

import sdmitry.systems.GamingSystemWithNegation
import scala.collection.mutable.ArrayBuffer

class DiceEngine(val pool: List[D]):
    /**
    * Calculate statistics from given pool for given type of system
    * @return List of gaming statistics
    */
    def faceToFaceNegating[T <: GamingSystemWithNegation](system: T): List[String] =
        system.explain(withOutcomes().map ( outcome => system.negation(outcome) ))

    

    /**
      * Calculate all possible outcomes from given dice pool
      * @return List of possible outcomes represented by list of dice results
      */
    def withOutcomes(): Iterable[Iterable[Res]] =
        pool.map {d => d.possibleOutcomes()}
            .foldRight(ArrayBuffer(ArrayBuffer.empty[Res])) { (currentList, acc) =>
                val newAcc = ArrayBuffer[ArrayBuffer[Res]]()
                for {
                    elem <- currentList
                    rest <- acc
                } {
                  newAcc += (elem +: rest)
                }
                newAcc
            }
