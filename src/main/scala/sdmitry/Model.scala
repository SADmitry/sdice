package sdmitry

trait DiceRandom:
    def random(low: Int, high: Int): Int

given systemRandom: DiceRandom with
    def random(start: Int, end: Int): Int =
        val rnd = new scala.util.Random
        start + rnd.nextInt( (end - start) + 1 )


class D(val sides: Int, val label: Option[String] = None, val playerId: Option[Int] = None):
    def roll(using randomizer: DiceRandom): Res =
        Res(randomizer.random(1, sides), this)

    def possibleOutcomes(): List[Res] =
        (1 to sides).map {i => Res(i, this)}.toList

    override def toString(): String =
        val optionals = Set(label, playerId).filter(_ != None).mkString(", ")
        if (optionals.isBlank())
            s"D($sides)"
        else
            s"D($sides, $optionals)"

    override def equals(that: Any): Boolean =
        that match
            case d: D =>
                this.sides == d.sides && this.label == d.label && this.playerId == d.playerId
            case _ =>
                false
        


class Res(val res: Int, val d: D):
    def explain(annotation: Map[String, Map[Int, String]]): String =
        d.label match
            case None        => ""
            case Some(label) => annotation.get(label) match
                    case None      => ""
                    case Some(map) => map.get(res) match
                        case Some(value) => value 
                        case None        => ""

    override def toString(): String = s"Res($res, $d)"
    override def equals(that: Any): Boolean =
        that match
            case r: Res =>
                this.d == r.d && this.res == r.res
            case _ =>
                false