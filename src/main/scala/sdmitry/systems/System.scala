package sdmitry.systems

import sdmitry.Dice
import sdmitry.Res
import sdmitry.Dice

trait GamingSystem[R, D <: Dice[R]]:
    /**
      * @param outsomes possible outcomes simulated by the engine
      * @return explained statistics depending on a gaming system
      */
    def explain(outsomes: Iterable[Iterable[Res[R, D]]]): List[String]

trait GamingSystemWithNegation[R, D <: Dice[R]] extends GamingSystem[R, D]:
    /**
      * Rule is applied to one outcome and returns updated result eliminating
      * dice which should be negated following gaming mechanic
      * @param outcome is a list of modelled result which may possibly happen
      * @return updated list containing only dice that should be resolved
      */
    def negation(outcome: Iterable[Res[R, D]]): Iterable[Res[R, D]]