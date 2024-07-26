package sdmitry

import scala.collection.mutable.ArrayBuffer
import sdmitry.D2
import sdmitry.D3
import sdmitry.D6

class EngineSpec extends munit.FunSuite:
  test("Outcomes of 1d6 should be 6 results") {
    val pool = Seq(D6())
    val obtained = DiceEngine[Int, D6](pool).outcomes()
    val expected = List(
        ArrayBuffer(Res(1, D6())), 
        ArrayBuffer(Res(2, D6())),
        ArrayBuffer(Res(3, D6())),
        ArrayBuffer(Res(4, D6())),
        ArrayBuffer(Res(5, D6())),
        ArrayBuffer(Res(6, D6()))
    )
    assertEquals(obtained, Outcomes[Int, D6](expected))
  }

  test("Outcomes of 2d3 should be 9 results") {
    val pool = Seq(D3(), D3())
    val obtained = DiceEngine[Int, D3](pool).outcomes()
    val expected = List(
        ArrayBuffer(Res(1, D3()), Res(1, D3())), 
        ArrayBuffer(Res(1, D3()), Res(2, D3())),
        ArrayBuffer(Res(1, D3()), Res(3, D3())),
        ArrayBuffer(Res(2, D3()), Res(1, D3())),
        ArrayBuffer(Res(2, D3()), Res(2, D3())),
        ArrayBuffer(Res(2, D3()), Res(3, D3())),
        ArrayBuffer(Res(3, D3()), Res(1, D3())),
        ArrayBuffer(Res(3, D3()), Res(2, D3())),
        ArrayBuffer(Res(3, D3()), Res(3, D3())),
    )
    assertEquals(obtained, Outcomes[Int, D3](expected))
  }

  test("Outcomes of 1d2 and 1d6 should be 12 results") {
    val pool: Seq[Dice[Int]] = Seq(D2(), D6())
    val obtained = DiceEngine[Int, Dice[Int]](pool).outcomes()
    val expected = List(
        ArrayBuffer(Res[Int, D2 | D6](1, D2()), Res(1, D6())), 
        ArrayBuffer(Res(1, D2()), Res(2, D6())),
        ArrayBuffer(Res(1, D2()), Res(3, D6())),
        ArrayBuffer(Res(1, D2()), Res(4, D6())),
        ArrayBuffer(Res(1, D2()), Res(5, D6())),
        ArrayBuffer(Res(1, D2()), Res(6, D6())),
        ArrayBuffer(Res(2, D2()), Res(1, D6())),
        ArrayBuffer(Res(2, D2()), Res(2, D6())),
        ArrayBuffer(Res(2, D2()), Res(3, D6())),
        ArrayBuffer(Res(2, D2()), Res(4, D6())),
        ArrayBuffer(Res(2, D2()), Res(5, D6())),
        ArrayBuffer(Res(2, D2()), Res(6, D6())),
    )
    assertEquals(obtained, Outcomes[Int, Dice[Int]](expected))
  }
