package sdmitry.systems

import sdmitry.DiceEngine
import sdmitry.Res

class JoanOfArkSpec extends munit.FunSuite:
    test("Joan of Ark system on small pool") {
        val joanOfArkTestPool = Seq(
            DRed(1), DBlack(2)
        )

        val engine = DiceEngine(joanOfArkTestPool)
        val joa = new JoanOfArk
        val obtained = engine.statisticsNegating(joa)
        val expected = Seq(
            "59% of dealing no damage",
            "8% of inflicting push",
            "16% of inflicting disrupt",
            "16% of inflicting sword"
        )
        assertEquals(obtained, expected)
    }

    test("Joan of Ark system on test pool") {
        val joanOfArkTestPool = Seq(
            DRed(1), DYellow(1), DYellow(1), DWhite(1),
            DBlack(2), DBlack(2)
        )

        val engine = DiceEngine(joanOfArkTestPool)
        val joa = new JoanOfArk
        val obtained = engine.statisticsNegating(joa)
        val expected = Seq(
            "25% of dealing no damage",
            "38% of inflicting push",
            "30% of inflicting disrupt",
            "6% of inflicting sword"
        )
        assertEquals(obtained, expected)
    }
