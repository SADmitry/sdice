package sdmitry

import sdmitry.systems.GamingSystem
import sdmitry.systems.GamingSystemWithNegationInRange

class DiceEngine[R, D <: Dice[R]](val pool: Seq[D]):
    import scala.collection.mutable.Map

    def statisticsNegating[G <: GamingSystem[R, D]](system: G): Seq[String] = 
        val outcomes = pool.map(d => d.possibleOutcomes().map(o => Res[R, D](o, d)))
        val tags = applySystemExplaining(outcomes, system)
        system.explain(tags.toMap)

    private def applySystemExplaining[G <: GamingSystem[R, D]](
        lists: Seq[Seq[Res[R, D]]],
        system: G,
        tagsMap: Map[String, Long] = Map.empty,
        currentIndex: Int = 0,
        currentCombination: Seq[Res[R, D]] = Seq.empty
    ): Map[String, Long] =
        currentIndex match
            case s if s == lists.size =>
                val tags = system.classify(currentCombination)
                tags.foreach(t => tagsMap(t) = tagsMap.getOrElse(t, 0l) + 1)
                tagsMap
            case _                    =>
                lists(currentIndex).foldLeft(tagsMap) { (acc, element) =>
                    applySystemExplaining(lists, system, acc, currentIndex + 1, currentCombination :+ element)
                }
