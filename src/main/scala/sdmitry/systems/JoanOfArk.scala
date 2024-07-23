package sdmitry.systems

import sdmitry.systems.GamingSystem
import sdmitry.Res
import sdmitry.Dice
import sdmitry.DiceRandom

enum JoAResult:
    case Blank, Push, Disrupt, Sword, Shield

trait JoADice extends Dice[JoAResult]:
    override def roll(using randomizer: DiceRandom): JoAResult =
        randomizer.random(1, 5) match
            case 1 => JoAResult.Blank
            case 2 => JoAResult.Push
            case 3 => JoAResult.Disrupt
            case 4 => JoAResult.Sword
            case 5 => JoAResult.Shield

class DBlack(override val playerId: Option[Int] = Some(1)) extends JoADice:
    override def possibleOutcomes(): Seq[JoAResult] = Seq(
        JoAResult.Shield, JoAResult.Shield, JoAResult.Shield, JoAResult.Disrupt, JoAResult.Disrupt, JoAResult.Sword,
    )

class DRed(override val playerId: Option[Int] = Some(1)) extends JoADice:
    override def possibleOutcomes(): Seq[JoAResult] = Seq(
        JoAResult.Shield, JoAResult.Push, JoAResult.Disrupt, JoAResult.Disrupt, JoAResult.Sword, JoAResult.Sword
    )

class DYellow(override val playerId: Option[Int] = Some(1)) extends JoADice:
    override def possibleOutcomes(): Seq[JoAResult] = Seq(
        JoAResult.Shield, JoAResult.Push, JoAResult.Push, JoAResult.Disrupt, JoAResult.Blank, JoAResult.Blank
    )

class DWhite(override val playerId: Option[Int] = Some(1)) extends JoADice:
    override def possibleOutcomes(): Seq[JoAResult] = Seq(
        JoAResult.Shield, JoAResult.Shield, JoAResult.Push, JoAResult.Disrupt, JoAResult.Disrupt, JoAResult.Blank,
    )
    

object JoanOfArk extends GamingSystemWithNegation[JoAResult, JoADice]:
    override def negation(outcome: Iterable[Res[JoAResult, JoADice]]): Iterable[Res[JoAResult, JoADice]] =
        val firstPool = outcome.filter(_.d.playerId == Some(1))
        val secondPool = outcome.filter(_.d.playerId == Some(2))

        val pushesOfFirst = firstPool.filter(_.res == JoAResult.Push).size
        val disruptsOfFirst = firstPool.filter(_.res == JoAResult.Disrupt).size
        val swordsOfFirst = firstPool.filter(_.res == JoAResult.Sword).size
        val shieldsOfSecond = secondPool.filter(_.res == JoAResult.Shield).size

        val shield = Res[JoAResult, JoADice](JoAResult.Shield, DBlack())
        val push = Res[JoAResult, JoADice](JoAResult.Push, DBlack())
        val disrupt = Res[JoAResult, JoADice](JoAResult.Disrupt, DBlack())
        val sword = Res[JoAResult, JoADice](JoAResult.Sword, DBlack())

        val afterSwords = shieldsOfSecond - swordsOfFirst
        afterSwords match
            case 0 => 
                (1 to disruptsOfFirst).map(_ => disrupt) ++ (1 to pushesOfFirst).map(_ => push)
            case a if a < 0 =>
                (1 to Math.abs(a)).map(_ => sword) ++ (1 to disruptsOfFirst).map(_ => disrupt) ++ (1 to pushesOfFirst).map(_ => push)
            case a if a > 0 =>
                val afterDisrupts = Math.abs(a) - disruptsOfFirst
                afterDisrupts match
                    case 0 =>
                        (1 to pushesOfFirst).map(_ => push)
                    case d if d < 0 =>
                        (1 to Math.abs(d)).map(_ => disrupt) ++ (1 to pushesOfFirst).map(_ => push)
                    case d if d > 0 =>
                        val afterPushes = Math.abs(d) - pushesOfFirst
                        afterPushes match
                            case 0 => 
                                List()
                            case p if p < 0 =>
                                (1 to Math.abs(p)).map(_ => shield)
                            case p if p > 0 =>
                                (1 to Math.abs(p)).map(_ => push)

    override def explain(outsomes: Iterable[Iterable[Res[JoAResult, JoADice]]]): List[String] =
        val noDamageAmount = outsomes.filter(out => out.isEmpty).size + outsomes.filter(out => out.forall(r => r.res == JoAResult.Shield)).size
        val noDamageChance = BigDecimal(noDamageAmount) /  outsomes.size * 100

        val pushAmount = outsomes.filter( out => out.exists(r => r.res == JoAResult.Push)).size
        val pushChance = BigDecimal(pushAmount) /  outsomes.size * 100

        val disruptAmount = outsomes.filter( out => out.exists(r => r.res == JoAResult.Disrupt)).size
        val disruptChance = BigDecimal(disruptAmount) /  outsomes.size * 100

        val swordAmount = outsomes.filter( out => out.exists(r => r.res == JoAResult.Sword)).size
        val swordChance = BigDecimal(swordAmount) /  outsomes.size * 100

        List(
            s"${noDamageChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of dealing no damage",
            s"${pushChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of inflicting push",
            s"${disruptChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of inflicting disrupt",
            s"${swordChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of inflicting sword"
        )
