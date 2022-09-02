package csbook.combinatorial

/** We define a "permutation" of length n as an arrangement of the integers
  * from 0 to n-1, where each of these integers is represented exactly once.
  *
  * Implementing classes will need to decide what data structures are used and
  * how they are applied. Implementing classes realize the Permutation trait by
  * implementing several important methods:
  *
  * - n : The size of the permutation
  * - permValue : The value of the permutation at an index from 0 to n-1
  * - next : The next permutation
  *
  *  Client classes get the value of the permutation at a particular index by
  *  calling valueAt, and they get the next permutation in the sequence by
  *  calling next.
  */
trait Permutation {
  /** Accessor for the value of the integer at the ith index. By convention,
    * if the index is less than 0, we give Int.MinValue, and if the index is n
    * or larger we give Int.MaxValue.
    *
    * A runtime exception is thrown for values in the range 0..n-1. Implementing
    * classes will have to provide logic for those values based on the specific
    * data structures used and assumptions made.
    *
    * @param i The index of the permutation to access.
    *
    * @return The value of the integer at the ith position in the permutation.
    */
  final def valueAt(i: Int): Int = {
    if (i<0) Int.MinValue
    else if (i<n) permValue(i)
    else Int.MaxValue
  }

  /** Determines the value of the permutation element at index i, between 0 and
    * n-1. It is only used for those values. Implementing classes are required
    * to override this. This is where the data structures of the implementing
    * class are used.
    *
    * @param i The index in the permutation this value is associated with
    *
    * @return The value of the permutation at this index
    */
  protected def permValue(i: Int): Int

  /** The size of the permutation.
    *
    * @return The number of elements in the permutation.
    */
  def n: Int

  /** The permutation that follows this one in some order. It is up to
    * implementations to determine the order.
    *
    * @return The next permutation, or None if there is no next permutation.
    */
  def next: Option[Permutation]

  override def toString: String = {
    val builder = new StringBuilder
    for (i <- 0 until n) {
      builder.append(valueAt(i))
      if (i<n-1) builder.append(" ")
    }
    builder.result
  }
}

/** Abstract permutation class using fixed sized arrays as the underlying data
  * structure. The class leaves to subclasses the business of deciding on a
  * scheme for ordering permutations.
  *
  * @param vals An array of values (must be a permutation of integers) to
  *             build the permutation from.
  */
abstract class FixedArrayPermutation protected (vals: Array[Int]) extends Permutation {
  protected val values: Array[Int] = vals.clone

  def n: Int = vals.length

  override def permValue(i: Int): Int = values(i)
}

/** A class for lexicographically ordered permutations. Instances of this class
  * are immutable.
  *
  * Note that the constructor is private to insure that it can't be abused.
  * The input to the constructor must be a permutation, and so we control
  * construction through the companion object, which can call the private
  * constructor, but never does so except with a well-formed permutation.
  *
  * @param vals An array of values (must be a permutation of integers) to
  *             build the permutation from.
  */
class LexicalOrderPermutation private (vals: Array[Int])
  extends FixedArrayPermutation(vals) {

  /** Returns the next permutation in lexicographical order (if one exists),
    * or None otherwise.
    *
    *  @return The next permutation, or None if there is no next permutation.
    */
  def next: Option[Permutation] = {
    // The pivot position is the rightmost position in the permutation where
    // the permutation value at that position is less than the the value to
    // its right.
    def findPivotPosition: Int = {
      var testVal = n-2
      while (valueAt(testVal)>valueAt(testVal+1)) testVal=testVal-1
      testVal
    }
    // The pivot swap position is the position of the smallest value in the
    // post pivot that is greater than the pivot value.
    def findPivotSwapPosition(pivotPoint: Int): Int = {
      val pivotValue: Int = values(pivotPoint)
      var valueOfSmallestElement: Int = Int.MaxValue
      var indexOfSmallestElement: Int = Int.MinValue
      for (j <- pivotPoint+1 until n) {
        if (valueAt(j) < valueOfSmallestElement && valueAt(j) > pivotValue) {
          valueOfSmallestElement = valueAt(j)
          indexOfSmallestElement = j
        }
      }
      indexOfSmallestElement
    }
    def pivot(pivotPosition: Int): Array[Int] = {
      val vals = values.clone
      // Find the pivot swap position.
      val pivotSwapPosition = findPivotSwapPosition(pivotPosition)
      // Swap the values in the pivot position and the pivot swap position.
      vals(pivotPosition) = values(pivotSwapPosition)
      vals(pivotSwapPosition) = values(pivotPosition)
      vals
    }
    def reversePostPivot(pivotPosition: Int, vals: Array[Int]): Array[Int] = {
      var swapLeftIndex:Int = pivotPosition + 1
      var swapRightIndex:Int = n - 1
      while (swapLeftIndex < swapRightIndex) {
        val temp: Int = vals(swapLeftIndex)
        vals(swapLeftIndex) = vals(swapRightIndex)
        vals(swapRightIndex) = temp
        swapLeftIndex += 1
        swapRightIndex -= 1
      }
      vals
    }

    // Find the rightmost element that is smaller than the element to its right
    findPivotPosition match {
      case pivotPosition if pivotPosition<0 =>
        None
      case pivotPosition =>
        Some(new LexicalOrderPermutation(reversePostPivot(pivotPosition, pivot(pivotPosition))))
    }
  }
}


object LexicalOrderPermutation {
  /** Use this method to generate a lexical order permutation. This is a
    * surrogate for a user constructor for LexicalOrderPermutation.
    *
    * @param n The size of the permutation
    *
    * @return The first permutation, in lexicographic order, of the first n
    *         integers.
    */
  def apply(n: Int): Option[LexicalOrderPermutation] = {
    if (n>0) Some (new LexicalOrderPermutation((0 until n).toArray))
    else None
  }
}


trait Perm2 {
  def n: Int
  def valueAt(i: Int): Int
}

object Perm2 {
  def main(args: Array[String]) : Unit = {
    LexicalOrderPermutation(5) match {
      case Some(p) =>
        println(p.n)
        println(p.valueAt(-2))
        println(p.valueAt(p.n - 1))
        println(p.valueAt(p.n))
        //println(p.findPivotPosition)
        //println(p.findPivotSwapPosition(p.findPivotPosition))
        println(p)
        var next = p.next
        while (next.isDefined) {
          next match {
            case Some(perm) => println(perm)
              next = perm.next
            case _ =>
          }
        }
    }
  }
}
case class Perm2Base(n:Int) extends Perm2 {
  def valueAt(i: Int): Int = i match {
    case j if j<0  => -1
    case j if j>=n => -1
    case _    => i
  }
  def next = new Perm2Child(this)
}

class Perm2Child(predecessor: Perm2) extends Perm2 {
  def n: Int = predecessor.n
  def valueAt(i: Int): Int = predecessor.valueAt(i)

}