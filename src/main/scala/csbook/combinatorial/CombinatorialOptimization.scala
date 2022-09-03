package csbook.combinatorial

class CombinatorialOptimization(size: Int, objectiveFunction: Permutation=>Option[Double]) {
  protected var bestCase: Option[Permutation] = None
  protected var bestValue: Option[Double] = None

  var perm: Option[Permutation] = LexicalOrderPermutation(size)
  while (perm.isDefined) perm match {
    case Some(permutation) =>
      evaluate(permutation)
      perm = permutation.next
    case None => ;
  }

  def evaluate(permutation: Permutation): Unit = {
    objectiveFunction(permutation) match {
      case None => ;
      case Some(objectiveValue) =>
        bestValue match {
          case None =>
            bestValue = Some(objectiveValue)
            bestCase = Some(permutation)
          case Some(value) =>
            if (objectiveValue < value) {
              bestValue = Some(objectiveValue)
              bestCase = Some(permutation)
            }
        }
    }
  }

  def sortObjective[T](p: Permutation)(comparator: (T, T) => Double)(seq: Seq[T]): Option[Double] =
    if (p.n == seq.length) {
      var difference = 0.0
      for (i <- 1 until p.n) difference += comparator(seq(p.valueAt(i - 1)), seq(p.valueAt(i)))
      Some(difference)
    } else {
      None
    }
}
