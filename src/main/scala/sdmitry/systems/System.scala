package sdmitry.systems

import sdmitry.D
import sdmitry.Res

trait GamingSystem:
    /**
      * @param outsomes possible outcomes simulated by the engine
      * @return explained statistics depending on a gaming system
      */
    def explain(outsomes: List[List[Res[D]]]): List[String]

trait GamingSystemWithCustomDice:
    /**
      * @return a map of annotations for custom dice used in a game.
      * Key of the first map is the name of the die.
      * Second map is pairs of a face and its label
      */
    def annotations(): Map[String, Map[Int, String]]

trait GamingSystemWithNegation:
    /**
      * Rule is applied to one outcome and returns updated result eliminating
      * dice which should be negated following gaming mechanic
      * @param outcome is a list of modelled result which may possibly happen
      * @return updated list containing only dice that should be resolved
      */
    def negation(outcome: List[Res[D]]): List[Res[D]]