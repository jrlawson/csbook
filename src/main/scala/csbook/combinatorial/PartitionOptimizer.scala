package csbook.combinatorial

class PartitionOptimizer(size: Int, objectiveFunction: Combination=>Option[Double]) {
  def run(): Option[(Combination,Double)] = {
    var bestCase: Option[(Combination,Double)] = None

    def evaluate(combination: Combination): Unit = {
      objectiveFunction(combination) match {
        case None => ;
        case Some(objectiveValue) =>
          bestCase match {
            case None =>
              bestCase = Some((combination, objectiveValue))
            case Some(best) =>
              if (objectiveValue < best._2) {
                bestCase = Some(combination, objectiveValue)
              }
          }
      }
    }
    var comb: Option[Combination] = None //LexicalOrderPermutation(size)
    while (comb.isDefined) {
      evaluate(comb.get)
      comb = comb.get.next
    }
    bestCase
  }
}


object PartitionObjectiveFunction {
  def objective[T](localCost: (T, T) => Double)(seq: Seq[T])(p: Combination): Option[Double] =
    if (p.n == seq.length) {
      var sum = 0.0
      //for (i <- 1 until p.n) sum += localCost(seq(p(i - 1)), seq(p(i)))
      Some(sum)
    } else {
      None
    }
}
