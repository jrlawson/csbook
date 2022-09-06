package csbook.combinatorial

object IntegerSorting {
  def intObj(i: Int, j: Int): Double = if (j<i) 1.0 else 0.0
  val sortIntObjectiveFunction = ArrangementOptimizer.objective[Int](intObj)(_:Seq[Int])(_:Permutation)
}
object IntSortingExample {
  def main(args: Array[String]): Unit = {
    val data = Array(28, 17, 32, 54, 11)
    def myObjectiveFunction =
      IntegerSorting.sortIntObjectiveFunction(data, _:Permutation)
    new ArrangementOptimizer(data.length, myObjectiveFunction).run() match {
      case None => println("No results")
      case Some(result) => Common.printResult(result)
    }
  }
}


