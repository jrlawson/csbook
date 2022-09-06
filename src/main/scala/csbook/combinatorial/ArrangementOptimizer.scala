package csbook.combinatorial

class ArrangementOptimizer(size: Int, objectiveFunction: Permutation=>Option[Double]) {
  def run(): Option[(Permutation,Double)] = {
    var bestCase: Option[(Permutation,Double)] = None

    def evaluate(permutation: Permutation): Unit = {
      objectiveFunction(permutation) match {
        case None => ;
        case Some(objectiveValue) =>
          bestCase match {
            case None =>
              bestCase = Some((permutation, objectiveValue))
            case Some(best) =>
              if (objectiveValue < best._2) {
                bestCase = Some(permutation, objectiveValue)
              }
          }
      }
    }
    var perm: Option[Permutation] = LexicalOrderPermutation(size)
    while (perm.isDefined) {
      evaluate(perm.get)
      perm = perm.get.next
    }
    bestCase
  }
}
object ArrangementOptimizer {
  def objective[T](localCost: (T, T) => Double)(seq: Seq[T])(p: Permutation): Option[Double] =
    if (p.n == seq.length) {
      var sum = 0.0
      for (i <- 1 until p.n) sum += localCost(seq(p(i - 1)), seq(p(i)))
      Some(sum)
    } else {
      None
    }
}

object ArrangementObjectiveFunction {
  def objective[T](localCost: (T, T) => Double)(seq: Seq[T])(p: Permutation): Option[Double] =
    if (p.n == seq.length) {
      var sum = 0.0
      for (i <- 1 until p.n) sum += localCost(seq(p(i - 1)), seq(p(i)))
      Some(sum)
    } else {
      None
    }
}

