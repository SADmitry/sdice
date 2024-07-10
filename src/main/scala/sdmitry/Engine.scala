package sdmitry

import sdmitry.systems.GamingSystemWithNegation

class DiceEngine(val pool: List[D]):
    /**
      * Calculate statistics from given pool for given type of system
      * @return List of gaming statistics
      */
    def faceToFaceNegating[T <: GamingSystemWithNegation](system: T): List[String] =
        system.explain(outcomes().map ( (outcome: List[Res]) => system.negation(outcome) ))

    /**
      * Calculate all possible outcomes from given dice pool
      * @return List of possible outcomes represented by list of dice results
      */
    def outcomes(): List[List[Res]] =
        pool.map {d => d.possibleOutcomes()}
            .foldRight(List(List.empty[Res])) { (currentList, acc) =>
                currentList.flatMap(elem => acc.map(o => o ++ List(elem)))
            }
