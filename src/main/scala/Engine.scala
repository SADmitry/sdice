object DiceEngine:
    def outcomesOf(pool: List[D]): List[List[Res[D]]] =
        pool.map {d => d.possible()}
            .foldRight(List(List.empty[Res[D]])) { (currentList, acc) =>
                currentList.flatMap(elem => acc.map(o => o ++ List(elem)))
            }