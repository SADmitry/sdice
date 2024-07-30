package sdmitry.systems

import sdmitry.Res
import sdmitry.Dice
import sdmitry.D20
import sdmitry.DiceRandom

class Infinity(
    override val firstRange: (Res[Int, D20]) => Boolean,
    override val secondRange: (Res[Int, D20]) => Boolean
) extends GamingSystemWithNegationInRange[Int, D20]:
    override def classify(roll: Iterable[Res[Int, D20]]): Seq[String] =
        val firstPool = roll.filter(_.d.playerId == 1).filter(firstRange)
        val secondPool = roll.filter(_.d.playerId == 2).filter(secondRange)
        
        val secondFiltered = secondPool.filter(d => !firstPool.exists(fd => fd.res > d.res))
        val firstFiltered = firstPool.filter(d => !secondPool.exists(sd => sd.res >= d.res))

        val firstWin = (firstFiltered ++ secondFiltered).forall(r => r.d.playerId == 1)
        if (firstWin) Seq("success") else Seq("fail")

    override def explain(stats: Map[String, Long]): Seq[String] =
        val total = stats.values.sum
        val firstWinChance = BigDecimal(stats("success")) / total * 100
        Seq(
            s"${firstWinChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of 1st player win",
            s"${(100 - firstWinChance).setScale(0, BigDecimal.RoundingMode.UP)}% of 2nd player win",
        )