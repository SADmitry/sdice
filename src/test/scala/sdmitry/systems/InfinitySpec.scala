package sdmitry.systems

import sdmitry.DiceEngine
import sdmitry.Dice
import sdmitry.D20
import sdmitry.Res
import sdmitry.DiceRandom

class InfinitySpec extends munit.FunSuite:
    test("Infinity classify") {
        val infinity = new Infinity((r) => true, (r) => true)
        
        val roll1 = Seq(
            Res(5, D20(1)), Res(5, D20(2))
        )
        val obtained1 = infinity.classify(roll1)
        assertEquals(obtained1, Seq("fail"))

        val roll2 = Seq(
            Res(10, D20(1)), Res(5, D20(2))
        )
        val obtained2 = infinity.classify(roll2)
        assertEquals(obtained2, Seq("success"))

        val roll3 = Seq(
            Res(5, D20(1)), Res(10, D20(2))
        )
        val obtained3 = infinity.classify(roll3)
        assertEquals(obtained3, Seq("fail"))

        val roll4 = Seq(
            Res(10, D20(1)), Res(10, D20(1)), Res(5, D20(2))
        )
        val obtained4 = infinity.classify(roll4)
        assertEquals(obtained4, Seq("success"))

        val roll5 = Seq(
            Res(10, D20(1)), Res(1, D20(2)), Res(5, D20(2))
        )
        val obtained5 = infinity.classify(roll5)
        assertEquals(obtained5, Seq("success"))

        val roll6 = Seq(
            Res(10, D20(1)), Res(10, D20(1)), Res(20, D20(2))
        )
        val obtained6 = infinity.classify(roll6)
        assertEquals(obtained6, Seq("fail"))
    }

    test("Infinity system on small test pool") {
        val infinityTestPool = Seq(
            D20(1), D20(2)
        )

        val infinity = new Infinity((r) => true, (r) => true)
        val engine = DiceEngine(infinityTestPool)
        val obtained = engine.statisticsNegating(infinity)
        val expected = List(
            "47% of 1st player win",
            "53% of 2nd player win"
        )
        assertEquals(obtained, expected)
    }

    test("Infinity system on test pool") {
        val infinityTestPool = Seq(
            D20(1), D20(1), D20(1),
            D20(2)
        )

        val infinity = new Infinity((r) => true, (r) => true)
        val engine = DiceEngine(infinityTestPool)
        val obtained = engine.statisticsNegating(infinity)
        val expected = List(
            "72% of 1st player win",
            "28% of 2nd player win"
        )
        assertEquals(obtained, expected)
    }