package csbook.combinatorial

object TSPFirstExample {
  private val cost = Array.ofDim[Double](5, 5)
  cost(0)(0) = 1000000.0
  cost(0)(1) = 2.0
  cost(0)(2) = 1000000.0
  cost(0)(3) = 12.0
  cost(0)(4) = 5.0

  cost(1)(0) = 2.0
  cost(1)(1) = 1000000.0
  cost(1)(2) = 4.0
  cost(1)(3) = 8.0
  cost(1)(4) = 1000000.0

  cost(2)(0) = 1000000.0
  cost(2)(1) = 4.0
  cost(2)(2) = 1000000.0
  cost(2)(3) = 4.0
  cost(2)(4) = 3.0

  cost(3)(0) = 12.0
  cost(3)(1) = 8.0
  cost(3)(2) = 3.0
  cost(3)(3) = 1000000.0
  cost(3)(4) = 10.0

  cost(4)(0) = 5.0
  cost(4)(1) = 1000000.0
  cost(4)(2) = 3.0
  cost(4)(3) = 10.0
  cost(4)(4) = 1000000.0

  def objectiveFunction(p: Permutation): Option[Double] = {
    var sum: Double = 0.0
    for (i <- 1 until p.n) sum += cost(p(i - 1))(p(i))
    sum += cost(p(p.n-1))(p(0))
    Some(sum)
  }

  def printResult(result: (Permutation, Double)): Unit = {
    for (i <- 0 until result._1.n) print((result._1(i)+65).toChar + " ")
    println()
    println("The total mileage is " + result._2)
  }

  def main(args: Array[String]): Unit = {
    new ArrangementOptimizer(5, objectiveFunction).run() match {
      case None => println("No results")
      case Some(result) => printResult(result)
    }
  }
}