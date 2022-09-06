package csbook.combinatorial

object Common {
  val data:Array[Int] = Array(28, 17, 32, 54, 11)
  def printResult(result: (Permutation, Double)): Unit = {
    for (i <- 0 until result._1.n) print(data(result._1(i)) + " ")
    println()
    println("The value of the objective function is " + result._2)
  }
}