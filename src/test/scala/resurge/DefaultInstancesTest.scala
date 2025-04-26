package resurge

import org.scalatest.funspec.AnyFunSpec
import resurge.DefaultInstances.given

import scala.collection.{immutable, mutable}

class DefaultInstancesTest extends AnyFunSpec:
  describe("Default values of integer types"):
    it("Default[Byte] should be 0"):
      assert(summon[Default[Byte]].default == 0.toByte)

    it("Default[Short] should be 0"):
      assert(summon[Default[Short]].default == 0.toShort)

    it("Default[Int] should be 0"):
     assert(summon[Default[Int]].default == 0)

    it("Default[Long] should be 0"):
      assert(summon[Default[Long]].default == 0L)

    it("Default[BigInt] should be 0"):
      assert(summon[Default[BigInt]].default == BigInt(0))

  describe("Default values of floating point number types"):
    it("Default[Float] should be 0.0"):
      assert(summon[Default[Float]].default == 0.0f)

    it("Default[Double] should be 0.0"):
      assert(summon[Default[Double]].default == 0.0)

    it("Default[BigDecimal] should be 0.0"):
      assert(summon[Default[BigDecimal]].default == BigDecimal(0.0))

  describe("Default boolean value"):
    it("Default[Boolean] should be false"):
      assert(!summon[Default[Boolean]].default)

  describe("Default values of characters and strings"):
    it("Default[Char] should be null character"):
      assert(summon[Default[Char]].default == '\u0000')

    it("Default[String] should be empty string"):
      assert(summon[Default[String]].default == "")

  describe("Default values of collection types"):
    it("Default[Option[Int]] should be None"):
      assert(summon[Default[Option[Int]]].default.isEmpty)

    it("Default[List[String]] should be Nil"):
      assert(summon[Default[List[String]]].default == Nil)

    it("Default[LazyLshould bet[Double]] should be empty"):
      assert(summon[Default[LazyList[Double]]].default == LazyList.empty[Double])

    it("Default[immutable.ArraySeq[Int]] should be empty"):
      assert(summon[Default[immutable.ArraySeq[Int]]].default == immutable.ArraySeq.empty[Int])

    it("Default[Vector[Char]] should be empty"):
      assert(summon[Default[Vector[Char]]].default == Vector.empty[Char])

    it("Default[immutable.Queue[String]] should be empty"):
      assert(summon[Default[immutable.Queue[String]]].default == immutable.Queue.empty[String])

    it("Default[Range] should be Range(0, 0)"):
      assert(summon[Default[Range]].default == Range(0, 0))

    it("Default[mutable.ArrayBuffer[Int]] should be empty"):
      assert(summon[Default[mutable.ArrayBuffer[Int]]].default == mutable.ArrayBuffer.empty[Int])

    it("Default[mutable.Lshould betBuffer[String]] should be empty"):
      assert(summon[Default[mutable.ListBuffer[String]]].default == mutable.ListBuffer.empty[String])

    it("Default[StringBuilder] should be empty"):
      assert(summon[Default[StringBuilder]].default.toString == "")

    it("Default[mutable.Queue[Int]] should be empty"):
      assert(summon[Default[mutable.Queue[Int]]].default == mutable.Queue.empty[Int])

    it("Default[mutable.ArraySeq[Double]] should be empty"):
      assert(summon[Default[mutable.ArraySeq[Double]]].default == mutable.ArraySeq.empty[Double])

    it("Default[mutable.Stack[Int]] should be empty"):
      assert(summon[Default[mutable.Stack[Int]]].default == mutable.Stack.empty[Int])

    it("Default[Array[String]] should be empty"):
      assert(summon[Default[Array[String]]].default.isEmpty)

    it("Default[mutable.ArrayDeque[Char]] should be empty"):
      assert(summon[Default[mutable.ArrayDeque[Char]]].default == mutable.ArrayDeque.empty[Char])

    it("Default[mutable.Map[Int, String]] should be empty"):
      assert(summon[Default[mutable.Map[Int, String]]].default == mutable.Map.empty[Int, String])

    it("Default[immutable.HashSet[String]] should be empty"):
      assert(summon[Default[immutable.HashSet[String]]].default == immutable.HashSet.empty[String])

    it("Default[immutable.HashMap[String, Int]] should be empty"):
      assert(summon[Default[immutable.HashMap[String, Int]]].default == immutable.HashMap.empty[String, Int])

    it("Default[immutable.TreeSet[Int]] should be empty"):
      assert(summon[Default[immutable.TreeSet[Int]]].default == immutable.TreeSet.empty[Int])

    it("Default[immutable.TreeMap[Int, String]] should be empty"):
      assert(summon[Default[immutable.TreeMap[Int, String]]].default == immutable.TreeMap.empty[Int, String])

    it("Default[immutable.BitSet] should be empty"):
      assert(summon[Default[immutable.BitSet]].default == immutable.BitSet.empty)

    it("Default[immutable.VectorMap[Int, String]] should be empty"):
      assert(summon[Default[immutable.VectorMap[Int, String]]].default == immutable.VectorMap.empty[Int, String])

    it("Default[immutable.ListMap[Int, String]] should be empty"):
      assert(summon[Default[immutable.ListMap[Int, String]]].default == immutable.ListMap.empty[Int, String])

    it("Default[mutable.HashSet[Double]] should be empty"):
      assert(summon[Default[mutable.HashSet[Double]]].default == mutable.HashSet.empty[Double])

    it("Default[mutable.HashMap[String, Int]] should be empty"):
      assert(summon[Default[mutable.HashMap[String, Int]]].default == mutable.HashMap.empty[String, Int])

    it("Default[mutable.WeakHashMap[String, Int]] should be empty"):
      assert(summon[Default[mutable.WeakHashMap[String, Int]]].default == mutable.WeakHashMap.empty[String, Int])

    it("Default[mutable.BitSet] should be empty"):
      assert(summon[Default[mutable.BitSet]].default == mutable.BitSet.empty)

    it("Default[mutable.TreeSet[Int]] should be empty"):
      assert(summon[Default[mutable.TreeSet[Int]]].default == mutable.TreeSet.empty[Int])
