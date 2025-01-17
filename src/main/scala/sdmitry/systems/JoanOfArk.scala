package sdmitry.systems

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

case class DBlack(override val playerId: Int = 1) extends JoADice:
    override def possibleOutcomes(): Seq[JoAResult] = Seq(
        JoAResult.Shield, JoAResult.Shield, JoAResult.Shield, JoAResult.Disrupt, JoAResult.Disrupt, JoAResult.Sword,
    )

case class DRed(override val playerId: Int = 1) extends JoADice:
    override def possibleOutcomes(): Seq[JoAResult] = Seq(
        JoAResult.Shield, JoAResult.Push, JoAResult.Disrupt, JoAResult.Disrupt, JoAResult.Sword, JoAResult.Sword
    )

case class DYellow(override val playerId: Int = 1) extends JoADice:
    override def possibleOutcomes(): Seq[JoAResult] = Seq(
        JoAResult.Shield, JoAResult.Push, JoAResult.Push, JoAResult.Disrupt, JoAResult.Blank, JoAResult.Blank
    )

case class DWhite(override val playerId: Int = 1) extends JoADice:
    override def possibleOutcomes(): Seq[JoAResult] = Seq(
        JoAResult.Shield, JoAResult.Shield, JoAResult.Push, JoAResult.Disrupt, JoAResult.Disrupt, JoAResult.Blank,
    )
    

class JoanOfArk extends GamingSystem[JoAResult, JoADice]:
    override def classify(roll: Iterable[Res[JoAResult, JoADice]]): Seq[String] =
        val firstPool = roll.filter(_.d.playerId == 1)
        val secondPool = roll.filter(_.d.playerId == 2)

        val pushesOfFirst = firstPool.filter(_.res == JoAResult.Push).size
        val disruptsOfFirst = firstPool.filter(_.res == JoAResult.Disrupt).size
        val swordsOfFirst = firstPool.filter(_.res == JoAResult.Sword).size
        val shieldsOfSecond = secondPool.filter(_.res == JoAResult.Shield).size

        val shield = Res[JoAResult, JoADice](JoAResult.Shield, DBlack())
        val push = Res[JoAResult, JoADice](JoAResult.Push, DBlack())
        val disrupt = Res[JoAResult, JoADice](JoAResult.Disrupt, DBlack())
        val sword = Res[JoAResult, JoADice](JoAResult.Sword, DBlack())

        val afterSwords = shieldsOfSecond - swordsOfFirst
        val resolved = afterSwords match
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
                            case p if p < 0 =>
                                (1 to Math.abs(p)).map(_ => shield)
                            case _ =>
                                List()

        var result = Seq[String]()
        if (resolved.isEmpty || resolved.forall(r => r.res == JoAResult.Shield))
            result = result :+ "fail"
        if (resolved.exists(r => r.res == JoAResult.Push))
            result = result :+ "push"
        if (resolved.exists(r => r.res == JoAResult.Disrupt))
            result = result :+ "disrupt"
        if (resolved.exists(r => r.res == JoAResult.Sword))
            result = result :+ "sword"
        result

    override def explain(stats: Map[String, Long]): Seq[String] =
        val total = stats.values.sum

        val pushChance = BigDecimal(stats("push")) / total * 100
        val disruptChance = BigDecimal(stats("disrupt")) / total * 100
        val swordChance = BigDecimal(stats("sword")) / total * 100

        Seq(
            s"${(BigDecimal(100) - pushChance - disruptChance - swordChance).setScale(0, BigDecimal.RoundingMode.UP)}% of dealing no damage",
            s"${pushChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of inflicting push",
            s"${disruptChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of inflicting disrupt",
            s"${swordChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of inflicting sword"
        )
