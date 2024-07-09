trait DiceRandom:
    def random(low: Int, high: Int): Int

given systemRandom: DiceRandom with
    def random(start: Int, end: Int): Int =
        val rnd = new scala.util.Random
        start + rnd.nextInt( (end - start) + 1 )


class D(val sides: Int, val label: Option[String] = None):
    def roll(using randomizer: DiceRandom): Res[D] =
        Res(randomizer.random(1, sides), this)

    def possibleOutcomes(): List[Res[D]] =
        (1 to sides).map {i => Res(i, this)}.toList

    override def toString(): String =
        label match
            case None        => s"D($sides)"
            case Some(value) => s"D($sides, $label)"

    override def equals(that: Any): Boolean =
        that match
            case d: D =>
                this.sides == d.sides && this.label == d.label
            case _ =>
                false
        


class Res[+T <: D](val res: Int, val d: T):
    override def toString(): String = s"Res($res, $d)"
    override def equals(that: Any): Boolean =
        that match
            case r: Res[_] =>
                this.d == r.d && this.res == r.res
            case _ =>
                false