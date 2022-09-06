package csbook.combinatorial

object IntegerScattering {
  def intObj(i: Int, j: Int): Double = -(j - i).abs
  val scatterIntObjectiveFunction = ArrangementOptimizer.objective[Int](intObj)(_:Seq[Int])(_:Permutation)
}
object IntegerScatterExample {
  def main(args: Array[String]): Unit = {
    def myObjectiveFunction = IntegerScattering.scatterIntObjectiveFunction(Common.data, _:Permutation)
    val optimizer=new ArrangementOptimizer(Common.data.length, myObjectiveFunction)
    println("Created the scatter optimizer")
    optimizer.run() match {
      case None => println("No results")
      case Some(result) => Common.printResult(result)
    }
  }
}
