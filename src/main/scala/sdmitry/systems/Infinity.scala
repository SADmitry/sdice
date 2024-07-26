package sdmitry.systems

import sdmitry.Res
import sdmitry.Dice
import sdmitry.D20
import sdmitry.DiceRandom

object Infinity extends GamingSystemWithNegationInRange[Int, D20]:
    override def negation(
        outcome: Iterable[Res[Int, D20]],
        firstRange: (Res[Int, D20]) => Boolean,
        secondRange: (Res[Int, D20]) => Boolean
    ): Iterable[Res[Int, D20]] =
        val firstPool = outcome.filter(_.d.playerId == 1).filter(firstRange)
        val secondPool = outcome.filter(_.d.playerId == 2).filter(secondRange)
        
        val secondFiltered = secondPool.filter(d => !firstPool.exists(fd => fd.res >= d.res))
        val firstFiltered = firstPool.filter(d => !secondPool.exists(sd => sd.res >= d.res))

        firstFiltered ++ secondFiltered

    override def explain(outsomes: Iterable[Iterable[Res[Int, D20]]]): List[String] =
        val firstWin = outsomes.filter(o => o.forall(os => os.d.playerId == 1))
        val firstWinChance = BigDecimal(firstWin.size) / outsomes.size * 100

        List(
            s"${firstWinChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of 1st player win",
            s"${(100 - firstWinChance).setScale(0, BigDecimal.RoundingMode.DOWN)}% of 2nd player win",
        )