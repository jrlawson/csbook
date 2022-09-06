package csbook.combinatorial

import scala.collection.mutable.ArrayBuffer

trait Combination {
  def n: Int
  //def k: Int
  def choices: Seq[Int]
  //def from: Seq[Int]
  def next: Option[Combination]
}
class FixedArrayCombination(v: Long, size: Int) extends Combination {
  def n: Int = size

  def choices: Seq[Int] = {
    val onBits = new ArrayBuffer[Int]()
    var mask = 1L
    for (i <- 0 until n) {
      if ((v & mask) != 0) onBits += i
      mask <<= 1
    }
    onBits.toArray[Int]
  }

  def next: Option[Combination] = {
    if (v==(1<<n)-1) {
      None
    } else {
      Some(new FixedArrayCombination(v+1, size))
    }
  }
}

object FixedArrayCombination {
  def main(args: Array[String]): Unit = {
    val comb = new FixedArrayCombination(0L, 5)
    println(comb.choices)
    println(comb.next.get.choices)
  }
}


