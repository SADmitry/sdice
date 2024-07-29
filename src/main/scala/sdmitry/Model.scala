package sdmitry


case class Res[+R, +D <: Dice[R]](val res: R, val d: D)

trait Dice[+R]:
    def playerId: Int = 1
    def roll(using randomizer: DiceRandom): R
    def possibleOutcomes(): Seq[R]

case class D2(override val playerId: Int = 1) extends Dice[Int]:
    override def roll(using randomizer: DiceRandom): Int = randomizer.random(1, 2)
    override def possibleOutcomes(): Seq[Int] = (1 to 2)

case class D3(override val playerId: Int = 1) extends Dice[Int]:
    override def roll(using randomizer: DiceRandom): Int = randomizer.random(1, 3)
    override def possibleOutcomes(): Seq[Int] = (1 to 3)
                
case class D6(override val playerId: Int = 1) extends Dice[Int]:
    override def roll(using randomizer: DiceRandom): Int = randomizer.random(1, 6)
    override def possibleOutcomes(): Seq[Int] = (1 to 6)

case class D10(override val playerId: Int = 1) extends Dice[Int]:
    override def roll(using randomizer: DiceRandom): Int = randomizer.random(1, 10)
    override def possibleOutcomes(): Seq[Int] = (1 to 10)

case class D20(override val playerId: Int = 1) extends Dice[Int]:
    override def roll(using randomizer: DiceRandom): Int = randomizer.random(1, 20)
    override def possibleOutcomes(): Seq[Int] = (1 to 20)


trait DiceRandom:
    def random(low: Int, high: Int): Int

given systemRandom: DiceRandom with
    def random(start: Int, end: Int): Int =
        val rnd = new scala.util.Random
        start + rnd.nextInt( (end - start) + 1 )
