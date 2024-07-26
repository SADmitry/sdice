package sdmitry

import scala.collection.mutable.ArrayBuffer

class D3(override val playerId: Option[Int] = None) extends Dice[Int]:
    override def roll(using randomizer: DiceRandom): Int = randomizer.random(1, 3)
    override def possibleOutcomes(): Seq[Int] = (1 to 3)

class D2(override val playerId: Option[Int] = None) extends Dice[Int]:
    override def roll(using randomizer: DiceRandom): Int = randomizer.random(1, 2)
    override def possibleOutcomes(): Seq[Int] = (1 to 2)

class EngineSpec extends munit.FunSuite:
  test("Outcomes of 1d6 should be 6 results") {
    val d6 = new D6
    val pool = Seq(d6)
    val obtained = DiceEngine[Int, D6](pool).outcomes()
    val expected = List(
        ArrayBuffer(Res(1, d6)), 
        ArrayBuffer(Res(2, d6)),
        ArrayBuffer(Res(3, d6)),
        ArrayBuffer(Res(4, d6)),
        ArrayBuffer(Res(5, d6)),
        ArrayBuffer(Res(6, d6))
    )
    assertEquals(obtained, Outcomes[Int, D6](expected))
  }

  test("Outcomes of 2d3 should be 9 results") {
    val firstD3 = new D3
    val secondD3 = new D3
    val pool = Seq(firstD3, secondD3)
    val obtained = DiceEngine[Int, D3](pool).outcomes()
    val expected = List(
        ArrayBuffer(Res(1, firstD3), Res(1, secondD3)), 
        ArrayBuffer(Res(1, firstD3), Res(2, secondD3)),
        ArrayBuffer(Res(1, firstD3), Res(3, secondD3)),
        ArrayBuffer(Res(2, firstD3), Res(1, secondD3)),
        ArrayBuffer(Res(2, firstD3), Res(2, secondD3)),
        ArrayBuffer(Res(2, firstD3), Res(3, secondD3)),
        ArrayBuffer(Res(3, firstD3), Res(1, secondD3)),
        ArrayBuffer(Res(3, firstD3), Res(2, secondD3)),
        ArrayBuffer(Res(3, firstD3), Res(3, secondD3)),
    )
    assertEquals(obtained, Outcomes[Int, D3](expected))
  }

  test("Outcomes of 1d2 and 1d6 should be 12 results") {
    val d2 = new D2
    val d6 = new D6
    val pool: Seq[D2 | D6] = Seq(d2, d6)
    val obtained = DiceEngine[Int, D2 | D6](pool).outcomes()
    val expected = List(
        ArrayBuffer(Res[Int, D2 | D6](1, d2), Res(1, d6)), 
        ArrayBuffer(Res(1, d2), Res(2, d6)),
        ArrayBuffer(Res(1, d2), Res(3, d6)),
        ArrayBuffer(Res(1, d2), Res(4, d6)),
        ArrayBuffer(Res(1, d2), Res(5, d6)),
        ArrayBuffer(Res(1, d2), Res(6, d6)),
        ArrayBuffer(Res(2, d2), Res(1, d6)),
        ArrayBuffer(Res(2, d2), Res(2, d6)),
        ArrayBuffer(Res(2, d2), Res(3, d6)),
        ArrayBuffer(Res(2, d2), Res(4, d6)),
        ArrayBuffer(Res(2, d2), Res(5, d6)),
        ArrayBuffer(Res(2, d2), Res(6, d6)),
    )
    assertEquals(obtained, Outcomes[Int, D2 | D6](expected))
  }
