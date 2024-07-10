package sdmitry

object DiceEngine:
    def outcomesOf(pool: List[D]): List[List[Res[D]]] =
        pool.map {d => d.possibleOutcomes()}
            .foldRight(List(List.empty[Res[D]])) { (currentList, acc) =>
                currentList.flatMap(elem => acc.map(o => o ++ List(elem)))
            }

    def faceToFaceNegating(
        outcomes: List[List[Res[D]]],
        annotations: Map[String, Map[Int, String]],
        negatingRule: (List[Res[D]]) => List[Res[D]],
        statsCollect: (List[List[Res[D]]]) => List[String]
    ): List[String] =
        statsCollect(
            outcomes.map ( (outcome: List[Res[D]]) => negatingRule(outcome) )
        )