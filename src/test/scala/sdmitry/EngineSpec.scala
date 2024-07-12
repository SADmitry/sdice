package sdmitry

import scala.collection.mutable.ArrayBuffer

class EngineSpec extends munit.FunSuite:
  test("Outcomes of 1d6 should be 6 results") {
    val pool = List(D(6))
    val obtained = DiceEngine(pool).outcomes()
    val expected = List(
        ArrayBuffer(Res(1, D(6))), 
        ArrayBuffer(Res(2, D(6))),
        ArrayBuffer(Res(3, D(6))),
        ArrayBuffer(Res(4, D(6))),
        ArrayBuffer(Res(5, D(6))),
        ArrayBuffer(Res(6, D(6)))
    )
    assertEquals(obtained, Outcomes(expected))
  }

  test("Outcomes of 2d3 should be 9 results") {
    val pool = List(D(3), D(3))
    val obtained = DiceEngine(pool).outcomes()
    val expected = List(
        ArrayBuffer(Res(1, D(3)), Res(1, D(3))), 
        ArrayBuffer(Res(1, D(3)), Res(2, D(3))),
        ArrayBuffer(Res(1, D(3)), Res(3, D(3))),
        ArrayBuffer(Res(2, D(3)), Res(1, D(3))),
        ArrayBuffer(Res(2, D(3)), Res(2, D(3))),
        ArrayBuffer(Res(2, D(3)), Res(3, D(3))),
        ArrayBuffer(Res(3, D(3)), Res(1, D(3))),
        ArrayBuffer(Res(3, D(3)), Res(2, D(3))),
        ArrayBuffer(Res(3, D(3)), Res(3, D(3))),
    )
    assertEquals(obtained, Outcomes(expected))
  }

  test("Outcomes of 1d2 and 1d6 should be 12 results") {
    val pool = List(D(2), D(6))
    val obtained = DiceEngine(pool).outcomes()
    val expected = List(
        ArrayBuffer(Res(1, D(2)), Res(1, D(6))), 
        ArrayBuffer(Res(1, D(2)), Res(2, D(6))),
        ArrayBuffer(Res(1, D(2)), Res(3, D(6))),
        ArrayBuffer(Res(1, D(2)), Res(4, D(6))),
        ArrayBuffer(Res(1, D(2)), Res(5, D(6))),
        ArrayBuffer(Res(1, D(2)), Res(6, D(6))),
        ArrayBuffer(Res(2, D(2)), Res(1, D(6))),
        ArrayBuffer(Res(2, D(2)), Res(2, D(6))),
        ArrayBuffer(Res(2, D(2)), Res(3, D(6))),
        ArrayBuffer(Res(2, D(2)), Res(4, D(6))),
        ArrayBuffer(Res(2, D(2)), Res(5, D(6))),
        ArrayBuffer(Res(2, D(2)), Res(6, D(6))),
    )
    assertEquals(obtained, Outcomes(expected))
  }
