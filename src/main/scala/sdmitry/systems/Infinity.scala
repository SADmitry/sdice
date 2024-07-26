package sdmitry.systems

import sdmitry.Res
import sdmitry.Dice
import sdmitry.DiceRandom

object Infinity extends GamingSystemWithNegationInRange[Int, Dice[Int]]:
    override def negation(
        outcome: Iterable[Res[Int, Dice[Int]]],
        firstRange: (Res[Int, Dice[Int]]) => Boolean,
        secondRange: (Res[Int, Dice[Int]]) => Boolean
    ): Iterable[Res[Int, Dice[Int]]] =
        val firstPool = outcome.filter(_.d.playerId == Some(1)).filter(firstRange)
        val secondPool = outcome.filter(_.d.playerId == Some(2)).filter(secondRange)
        
        val secondFiltered = secondPool.filter(d => !firstPool.exists(fd => fd.res >= d.res))
        val firstFiltered = firstPool.filter(d => !secondPool.exists(sd => sd.res >= d.res))

        firstFiltered ++ secondFiltered

    override def explain(outsomes: Iterable[Iterable[Res[Int, Dice[Int]]]]): List[String] = ???