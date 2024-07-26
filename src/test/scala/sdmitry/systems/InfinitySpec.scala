package sdmitry.systems

import sdmitry.DiceEngine
import sdmitry.Dice
import sdmitry.Res
import sdmitry.DiceRandom

class InfinitySpec extends munit.FunSuite:
    test("Infinity negation check") {
        val testOutcome = Iterable(
            Res(5, D20(Some(1))), Res(8, D20(Some(1))), Res(9, D20(Some(1))), Res(11, D20(Some(1))), Res(6, D20(Some(2))), Res(9, D20(Some(2)))
        )

        val obtained = Infinity.negation(testOutcome, (r) => true, (r) => true)
        val expected = Iterable(
            Res(11, D20(Some(1)))
        )

        assertEquals(obtained, expected)
    }

    test("Infinity system on test pool") {
        val infinityTestPool = Seq(
            D20(playerId = Some(1)),
            D20(playerId = Some(1)),
            D20(playerId = Some(1)),
            D20(playerId = Some(2))
        )

        val engine = DiceEngine(infinityTestPool)
        val outcomes = engine.outcomes()
        val resolve = outcomes.resolveNegatingRange(Infinity, (r) => true, (r) => true)
        val obtained = outcomes.explain(Infinity, resolve)
        val expected = List(
            "77% of 1st player win",
            "22% of 2nd player win"
        )
        assertEquals(obtained, expected)
    }