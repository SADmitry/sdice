trait DiceRandom:
    def random(low: Int, high: Int): Int

given systemRandom: DiceRandom with
    def random(start: Int, end: Int): Int =
        val rnd = new scala.util.Random
        start + rnd.nextInt( (end - start) + 1 )


trait Dice:
    def roll(using randomizer: DiceRandom): Result

case class D(val sides: Int, val label: Option[String] = None) extends Dice:
    override def roll(using randomizer: DiceRandom): Result =
        Res(this, randomizer.random(1, sides))


trait Result

case class Res[+T <: Dice](val d: T, res: Int) extends Result