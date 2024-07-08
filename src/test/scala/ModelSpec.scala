
class ModelSpec extends munit.FunSuite {
  test("Roll should return proper result") {
    given constantRandom: DiceRandom with
        def random(start: Int, end: Int): Int = 4

    val d6 = D(6)
    val obtained = d6.roll(using constantRandom)
    val expected = Res(d6, 4)
    assertEquals(obtained, expected)
  }
}
