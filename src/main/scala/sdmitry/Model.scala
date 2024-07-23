package sdmitry

trait DiceRandom:
    def random(low: Int, high: Int): Int

given systemRandom: DiceRandom with
    def random(start: Int, end: Int): Int =
        val rnd = new scala.util.Random
        start + rnd.nextInt( (end - start) + 1 )


trait Dice[R]:
    def playerId: Option[Int] = None
    def roll(using randomizer: DiceRandom): R
    def possibleOutcomes(): Seq[R]
                
class D6(override val playerId: Option[Int] = None) extends Dice[Int]:
    override def roll(using randomizer: DiceRandom): Int = randomizer.random(1, 6)

    override def possibleOutcomes(): Seq[Int] = (1 to 6)

    override def toString(): String =
        playerId match
            case None => "d6"
            case Some(id) => s"d6, player $id"

    override def equals(that: Any): Boolean =
        that match
            case d: D6 => this.playerId == d.playerId
            case _     => false