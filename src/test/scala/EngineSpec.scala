
class EngineSpec extends munit.FunSuite {
  test("Outcomes of 1d6 should be 6 results") {
    val pool = List(D(6))
    val obtained = DiceEngine.outcomesOf(pool)
    val expected = List(
        List(Res(1, D(6))), 
        List(Res(2, D(6))),
        List(Res(3, D(6))),
        List(Res(4, D(6))),
        List(Res(5, D(6))),
        List(Res(6, D(6)))
    )
    assertEquals(obtained, expected)
  }

  test("Outcomes of 2d3 should be 9 results") {
    val pool = List(D(3), D(3))
    val obtained = DiceEngine.outcomesOf(pool)
    val expected = List(
        List(Res(1, D(3)), Res(1, D(3))), 
        List(Res(2, D(3)), Res(1, D(3))),
        List(Res(3, D(3)), Res(1, D(3))),
        List(Res(1, D(3)), Res(2, D(3))),
        List(Res(2, D(3)), Res(2, D(3))),
        List(Res(3, D(3)), Res(2, D(3))),
        List(Res(1, D(3)), Res(3, D(3))),
        List(Res(2, D(3)), Res(3, D(3))),
        List(Res(3, D(3)), Res(3, D(3))),
    )
    assertEquals(obtained, expected)
  }

  test("Outcomes of 1d2 and 1d6 should be 12 results") {
    val pool = List(D(2), D(6))
    val obtained = DiceEngine.outcomesOf(pool)
    val expected = List(
        List(Res(1, D(6)), Res(1, D(2))), 
        List(Res(2, D(6)), Res(1, D(2))),
        List(Res(3, D(6)), Res(1, D(2))),
        List(Res(4, D(6)), Res(1, D(2))),
        List(Res(5, D(6)), Res(1, D(2))),
        List(Res(6, D(6)), Res(1, D(2))),
        List(Res(1, D(6)), Res(2, D(2))),
        List(Res(2, D(6)), Res(2, D(2))),
        List(Res(3, D(6)), Res(2, D(2))),
        List(Res(4, D(6)), Res(2, D(2))),
        List(Res(5, D(6)), Res(2, D(2))),
        List(Res(6, D(6)), Res(2, D(2))),
    )
    assertEquals(obtained, expected)
  }

  test("Joan of Ark test") {
    val joaOut = DiceEngine.outcomesOf(DiceEngine.joanOfArkTestPool)
    val obtained = DiceEngine.faceToFaceNegating(joaOut, DiceEngine.joanOfArkAnnotations, DiceEngine.joanOfArkNegation, DiceEngine.joanOfArkStats)
    val expected = List("44.4 of dealing no damage", "55.6 of dealing at least one damage")
    assertEquals(obtained, expected)
  }
}
