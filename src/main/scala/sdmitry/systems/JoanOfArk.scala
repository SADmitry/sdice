package sdmitry.systems

import sdmitry.systems.GamingSystem
import sdmitry.Res
import sdmitry.D

object JoanOfArk extends GamingSystemWithNegation:
    val push = Res(3, D(6, Some("white")))
    val disrupt = Res(4, D(6, Some("red")))
    val sword = Res(5, D(6, Some("red")))
    val shield = Res(1, D(6, Some("black")))

    override def negation(outcome: Iterable[Res]): Iterable[Res] =
        val pushesFirst = outcome.filter(res => res.d.playerId == Some(1)).filter(res => 
            res.d.label match
                case Some(l) if l == "red" && res.res == 2                   => true
                case Some(l) if l == "yellow" && Seq(2, 3).contains(res.res) => true
                case Some(l) if l == "white" && res.res == 3                 => true
                case _                                                       => false  
        ).size

        val disruptsFirst = outcome.filter(res => res.d.playerId == Some(1)).filter(res => 
            res.d.label match
                case Some(l) if l == "red" && Seq(3, 4).contains(res.res)   => true
                case Some(l) if l == "black" && Seq(4, 5).contains(res.res) => true
                case Some(l) if l == "yellow" && res.res == 4               => true
                case Some(l) if l == "white" && Seq(4, 5).contains(res.res) => true
                case _                                                      => false  
        ).size

        val swordsFirst = outcome.filter(res => res.d.playerId == Some(1)).filter(res => 
            res.d.label match
                case Some(l) if l == "red" && Seq(5, 6).contains(res.res) => true
                case Some(l) if l == "black" && res.res == 6              => true
                case _                                                    => false  
        ).size

        val shieldsSecond = outcome.filter(res => res.d.playerId == Some(2)).filter(res => 
            res.d.label match
                case Some(l) if l == "red" && res.res == 1                     => true
                case Some(l) if l == "black" && Seq(1, 2, 3).contains(res.res) => true
                case Some(l) if l == "yellow" && res.res == 1                  => true
                case Some(l) if l == "white" && Seq(1, 2).contains(res.res)    => true
                case _                                                         => false  
        ).size

        val afterSwords = shieldsSecond - swordsFirst
        afterSwords match
            case 0 => 
                ((1 to disruptsFirst).map(_ => disrupt) ++ (1 to pushesFirst).map(_ => push)).toList
            case a if a < 0 =>
                ((1 to Math.abs(a)).map(_ => sword) ++ (1 to disruptsFirst).map(_ => disrupt) ++ (1 to pushesFirst).map(_ => push)).toList
            case a if a > 0 =>
                val afterDisrupts = Math.abs(a) - disruptsFirst
                afterDisrupts match
                    case 0 =>
                        (1 to pushesFirst).map(_ => push).toList
                    case d if d < 0 =>
                        ((1 to Math.abs(d)).map(_ => disrupt) ++ (1 to pushesFirst).map(_ => push)).toList
                    case d if d > 0 =>
                        val afterPushes = Math.abs(d) - pushesFirst
                        afterPushes match
                            case 0 => 
                                List()
                            case p if p < 0 =>
                                (1 to Math.abs(p)).map(_ => shield).toList
                            case p if p > 0 =>
                                (1 to Math.abs(p)).map(_ => push).toList

    override def explain(outsomes: Iterable[Iterable[Res]]): List[String] =
        val shield = Res(1, D(6, Some("black")))

        val noDamageAmount = outsomes.filter( out => out.isEmpty).size + outsomes.filter( out => out.forall( r => r == shield)).size
        val noDamageChance = BigDecimal(noDamageAmount) /  outsomes.size * 100

        val pushAmount = outsomes.filter( out => out.exists(r => r == push)).size
        val pushChance = BigDecimal(pushAmount) /  outsomes.size * 100

        val disruptAmount = outsomes.filter( out => out.exists(r => r == disrupt)).size
        val disruptChance = BigDecimal(disruptAmount) /  outsomes.size * 100

        val swordAmount = outsomes.filter( out => out.exists(r => r == sword)).size
        val swordChance = BigDecimal(swordAmount) /  outsomes.size * 100

        List(
            s"${noDamageChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of dealing no damage",
            s"${pushChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of inflicting push",
            s"${disruptChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of inflicting disrupt",
            s"${swordChance.setScale(0, BigDecimal.RoundingMode.DOWN)}% of inflicting sword"
        )

    override def annotations(): Map[String, Map[Int, String]] = Map(
        "red" -> Map(1 -> "shield", 2 -> "push", 3 -> "disrupt", 4 -> "disrupt", 5 -> "sword", 6 -> "sword"),
        "black" -> Map(1 -> "shield", 2 -> "shield", 3 -> "shield", 4 -> "disrupt", 5 -> "disrupt", 6 -> "sword"),
        "yellow" -> Map(1 -> "shield", 2 -> "push", 3 -> "push", 4 -> "disrupt", 5 -> "", 6 -> ""),
        "white" -> Map(1 -> "shield", 2 -> "shield", 3 -> "push", 4 -> "disrupt", 5 -> "disrupt", 6 -> ""),
    )