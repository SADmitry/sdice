package sdmitry.systems

import sdmitry.Res
import sdmitry.Dice
import sdmitry.D20
import sdmitry.DiceRandom

class Infinity(
    override val firstRange: (Res[Int, D20]) => Boolean,
    override val secondRange: (Res[Int, D20]) => Boolean
) extends GamingSystemWithNegationInRange[Int, D20]:
    override def negation(outcome: Iterable[Res[Int, D20]]): Iterable[Res[Int, D20]] =
        val firstPool = outcome.filter(_.d.playerId == 1).filter(firstRange)
        val secondPool = outcome.filter(_.d.playerId == 2).filter(secondRange)
        
        val secondFiltered = secondPool.filter(d => !firstPool.exists(fd => fd.res >= d.res))
        val firstFiltered = firstPool.filter(d => !secondPool.exists(sd => sd.res >= d.res))

        firstFiltered ++ secondFiltered

    override def classify(roll: Iterable[Res[Int, D20]]): Seq[String] =
        val firstWin = roll.forall(r => r.d.playerId == 1)
        if (firstWin) Seq("success") else Seq("fail")

    override def explain(stats: Map[String, Long]): Seq[String] =
        val total = stats.values.sum
        val firstWinChance = BigDecimal(stats("success")) / total * 100
        Seq(
            s"${firstWinChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of 1st player win",
            s"${(100 - firstWinChance).setScale(0, BigDecimal.RoundingMode.DOWN)}% of 2nd player win",
        )