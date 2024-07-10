package sdmitry.systems

import sdmitry.DiceEngine

class JoanOfArkSpec extends munit.FunSuite {

  test("Joan of Ark test") {
    val joaOut = DiceEngine.outcomesOf(JoanOfArk.joanOfArkTestPool)
    val obtained = DiceEngine.faceToFaceNegating(joaOut, JoanOfArk.joanOfArkAnnotations, JoanOfArk.joanOfArkNegation, JoanOfArk.joanOfArkStats)
    val expected = List("44.4 of dealing no damage", "55.6 of dealing at least one damage")
    assertEquals(obtained, expected)
  }
}
