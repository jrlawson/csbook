package csbook.perm

/** Used to take action on a permutation. When registered with a Perm object, the Perm object will call this callback
  * function when it finds a new permutation.
  */
trait PermCallback {
  /** Acts on a new permutation.
    *
    * @param perm A zero-indexed array representing the permutation that was identified.
    *
    * @return Whether or not the permutation generator should keep finding permutations now. In some problems, like
    *         combinatorial optimization problems, the callback function will always return true, and the permutation
    *         generator keeps going until all permutations have been tried, because the search is exhaustive and we
    *         need to try all of the possibilities before giving up. In other cases, like combinatorial search, we
    *         only need to generate permutations until we find what we are looking for.
    */
  def handlePerm(perm: Array[Int]): Boolean
}

/** A test PermCallback that simply prints out the permutation */
class PrintPermCallback extends PermCallback {
  def handlePerm(perm: Array[Int]): Boolean = {
    for (i <- perm.indices) print(perm(i))
    println()
    true
  }
}

/** A test PermCallback that prints out the permutation but signals the generator to stop after quitSteps permutations
  * have been generated.
  *
  * @param permutationsToGenerate  The number of permutations to generate before telling the permutation generator
  *                                to stop generating permutations
  */
class PrintPermCallbackWithAQuit(permutationsToGenerate: Int) extends PermCallback {
  var steps: Int = 0
  def handlePerm(data: Array[Int]): Boolean = {
    for (i <- data.indices) print(data(i))
    println()
    steps += 1
    steps < permutationsToGenerate
  }
}

/** A permutation generator. To use this class, construct a Perm giving the number of elements in the permutations,
  * and a callback function to call when a permutation is generated.
  *
  * @param n        The size of the permutation
  * @param callback A callback function to call when a permutation has been generated
  */
class Perm (n:Int, callback: PermCallback) {
  def length:Int = n
  private var swapCount = 0
  def swaps: Int = swapCount

  private var keepGoing: Boolean = true
  def cont: Boolean = keepGoing

  private val data = new Array[Int](n)
  for (i <- 0 until n) data(i)= i
  def value(i: Int) : Int = data(i)

  private def swap(i:Int, j:Int): Unit = {
    val temp = data(i)
    data(i) = data(j)
    data(j) = temp
    swapCount += 1
  }

  def generate() : Unit = heapsAlgorithm(n)

  /**  Uses Heap's algorithm to generate permutations. */
  private def heapsAlgorithm (size: Int): Unit = {
    // permutation will be displayed when size becomes 1.
    if (size == 1) {
      keepGoing = callback.handlePerm(data.clone)
      return
    }

    for (j <- 0 until size) {
      if (!keepGoing) return
      heapsAlgorithm(size - 1)

      //size is odd, swap first and last element, otherwise size if is even, swap jth and last element
      if (size % 2 == 1) swap(0, size - 1)
      else swap(j, size - 1)
    }
  }
}

object Perm {
  def main(args: Array[String]): Unit = {
    /*
    val perm = new Perm(3, new PrintPermCallbackWithAQuit(3))
    perm.generate()
    println("Swaps = " + perm.swaps)
    */
    val sorter = new BruteForceNumberSorter(List(28, 17, 32, 54, 11))
    sorter.sort()
    sorter.display()
  }
}

class NumberSorterCallback(sorter: BruteForceNumberSorter) extends PermCallback {
  var thePerm: Array[Int] = null

  private def test: Boolean = {
    for (i <- 0 until thePerm.length-1) {
      if (sorter.numbers(thePerm(i)) > sorter.numbers(thePerm(i + 1))) return true
    }
    // If you get this far, every number in the list is <= the number that follows it. So you can stop
    return false
  }

  def handlePerm(perm: Array[Int]): Boolean = {
    thePerm = perm
    return test
  }
}

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Seq
class BruteForceNumberSorter(nums: List[Int]) {
  def numbers = nums
  private val callback = new NumberSorterCallback(this)

  def sort(): Seq[Int] = {
    val perm = new Perm(numbers.length, callback)
    val sortedPerm = perm.generate()
    val result: ArrayBuffer[Int] = new ArrayBuffer[Int]
    for (i <- numbers.indices) result += numbers(callback.thePerm(i))
    result
  }

  def display() : Unit = {
    for (i <- callback.thePerm.indices) print(nums(callback.thePerm(i)) + " ")
    println()
  }
}

class GenericSorterCallback[T](sorter: BruteForceGenericSorter[T]) extends PermCallback {
  var thePerm: Array[Int] = null

  private def test: Boolean = {
    for (i <- 0 until thePerm.length-1) {
      if (sorter.o(sorter.stuff(thePerm(i)), sorter.stuff(thePerm(i + 1))) )return true
    }
    // If you get this far, every number in the list is <= the number that follows it. So you can stop
    return false
  }

  def handlePerm(perm: Array[Int]): Boolean = {
    thePerm = perm
    return test
  }
}

abstract class BruteForceGenericSorter[T](data: Array[T]) {
  def o(e1:T, e2:T): Boolean

  def stuff: Array[T] = data
  private val callback = new GenericSorterCallback[T](this)

  def sort(): Seq[T] = {
    val perm = new Perm(stuff.length, callback)
    val sortedPerm = perm.generate()
    val result: ArrayBuffer[T] = new ArrayBuffer[T]
    for (i <- stuff.indices) result += stuff(callback.thePerm(i))
    result
  }

  def display() : Unit = {
    for (i <- callback.thePerm.indices) print(data(callback.thePerm(i)) + " ")
    println()
  }
}

class BruteForceAbstractNumberSorter(data: Array[Int]) extends BruteForceGenericSorter[Int](data: Array[Int]) {
  def o(e1: Int, e2: Int): Boolean = e1 < e2
}
