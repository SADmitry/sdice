package sdmitry.systems

import sdmitry.DiceEngine
import sdmitry.Res

class JoanOfArkSpec extends munit.FunSuite:
    test("Joan of Ark negation check") {
        val disruptFirst = Res[JoAResult, JoADice](JoAResult.Disrupt, DBlack(playerId = Some(1)))
        val disruptSecond = Res[JoAResult, JoADice](JoAResult.Disrupt, DBlack(playerId = Some(2)))
        val swordFirst = Res[JoAResult, JoADice](JoAResult.Sword, DBlack(playerId = Some(1)))
        val shieldSecond = Res[JoAResult, JoADice](JoAResult.Shield, DBlack(playerId = Some(2)))

        val testOutcome = Seq(
            disruptFirst, disruptSecond, swordFirst, swordFirst, shieldSecond
        )

        val obtained = JoanOfArk.negation(testOutcome)
        val expected: List[Res[JoAResult, JoADice]] = List(
            swordFirst, disruptFirst
        )

        val obtainedAnnotated = obtained.map( r => r.explain()).mkString(", ")
        val expectedAnnotated = expected.map( r => r.explain()).mkString(", ")

        assertEquals(obtainedAnnotated, expectedAnnotated)
    }

    test("Joan of Ark system on test pool") {
        val joanOfArkTestPool = Seq(
            DRed(playerId = Some(1)),
            DYellow(playerId = Some(1)),
            DYellow(playerId = Some(1)),
            DWhite(playerId = Some(1)),
            DBlack(playerId = Some(2)),
            DBlack(playerId = Some(2))
        )

        val engine = DiceEngine(joanOfArkTestPool)
        val joaOut = engine.outcomes()
        val obtained = joaOut.faceToFaceNegating(JoanOfArk)
        val expected = List(
            "44% of dealing no damage",
            "56% of inflicting push",
            "39% of inflicting disrupt",
            "8% of inflicting sword"
        )
        assertEquals(obtained, expected)
    }
