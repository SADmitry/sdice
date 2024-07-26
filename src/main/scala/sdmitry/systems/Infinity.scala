package sdmitry.systems

import sdmitry.Res
import sdmitry.Dice
import sdmitry.DiceRandom

class D20(override val playerId: Option[Int] = None) extends Dice[Int]:
    override def roll(using randomizer: DiceRandom): Int = randomizer.random(1, 20)
    override def possibleOutcomes(): Seq[Int] = (1 to 20)

object Infinity extends GamingSystemWithNegationInRange[Int, D20]:
    override def negation(
        outcome: Iterable[Res[Int, D20]],
        firstRange: (Res[Int, D20]) => Boolean,
        secondRange: (Res[Int, D20]) => Boolean
    ): Iterable[Res[Int, D20]] =
        val firstPool = outcome.filter(_.d.playerId == Some(1)).filter(firstRange)
        val secondPool = outcome.filter(_.d.playerId == Some(2)).filter(secondRange)
        
        val secondFiltered = secondPool.filter(d => !firstPool.exists(fd => fd.res >= d.res))
        val firstFiltered = firstPool.filter(d => !secondPool.exists(sd => sd.res >= d.res))

        firstFiltered ++ secondFiltered

    override def explain(outsomes: Iterable[Iterable[Res[Int, D20]]]): List[String] =
        val firstWin = outsomes.filter(o => o.forall(os => os.d.playerId == Some(1)))
        val firstWinChance = BigDecimal(firstWin.size) / outsomes.size * 100

        List(
            s"${firstWinChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of 1st player win",
            s"${(100 - firstWinChance).setScale(0, BigDecimal.RoundingMode.DOWN)}% of 2nd player win",
        )