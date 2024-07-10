package sdmitry.systems

import sdmitry.DiceEngine
import sdmitry.D
import sdmitry.Res

class JoanOfArkSpec extends munit.FunSuite {

    test("Joan of Ark negation check") {
        val disruptFirst = Res(4, D(6, Some("red"), Some(1)))
        val disruptSecond = Res(4, D(6, Some("red"), Some(2)))
        val swordFirst = Res(5, D(6, Some("red"), Some(1)))
        val shieldSecond = Res(1, D(6, Some("black"), Some(2)))

        val testOutcome: List[Res[D]] = List(
            disruptFirst, disruptSecond, swordFirst, swordFirst, shieldSecond
        )

        val obtained = JoanOfArk.negation(testOutcome)
        val expected: List[Res[D]] = List(
            swordFirst, disruptFirst
        )

        val obtainedAnnotated = obtained.map( r => r.explain(JoanOfArk.annotations())).mkString(", ")
        val expectedAnnotated = expected.map( r => r.explain(JoanOfArk.annotations())).mkString(", ")

        assertEquals(obtainedAnnotated, expectedAnnotated)
    }

    test("Joan of Ark system on test pool") {
        val joanOfArkTestPool: List[D] = List(
            D(6, Some("red"), Some(1)),
            D(6, Some("yellow"), Some(1)),
            D(6, Some("yellow"), Some(1)),
            D(6, Some("white"), Some(1)),
            D(6, Some("black"), Some(2)),
            D(6, Some("black"), Some(2))
        )

        val joaOut = DiceEngine.outcomesOf(joanOfArkTestPool)
        val obtained = DiceEngine.faceToFaceNegating(joaOut, JoanOfArk.annotations(), JoanOfArk.negation, JoanOfArk.explain)
        val expected = List("44.4 of dealing no damage", "55.6 of dealing at least one damage")
        assertEquals(obtained, expected)
    }
}
