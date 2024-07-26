package sdmitry.systems

import sdmitry.DiceEngine
import sdmitry.Dice
import sdmitry.Res
import sdmitry.DiceRandom

class D20(override val playerId: Option[Int] = None) extends Dice[Int]:
    override def roll(using randomizer: DiceRandom): Int = randomizer.random(1, 20)
    override def possibleOutcomes(): Seq[Int] = (1 to 20)

class InfinitySpec extends munit.FunSuite:
    test("Infinity negation check") {
        val testOutcome: Iterable[Res[Int, Dice[Int]]] = Iterable(
            Res(5, D20(Some(1))), Res(8, D20(Some(1))), Res(9, D20(Some(1))), Res(11, D20(Some(1))), Res(6, D20(Some(2))), Res(9, D20(Some(2)))
        )

        val obtained = Infinity.negation(testOutcome, (r) => true, (r) => true)
        val expected: Iterable[Res[Int, D20]] = Iterable(
            Res(11, D20(Some(1)))
        )

        assertEquals(obtained, expected)
    }