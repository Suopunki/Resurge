import scala.collection.{immutable, mutable}
import scala.reflect.ClassTag


/** Implicit default values for many common data types. */
object DefaultInstances:

  /*****************
   * Integer types *
   *****************/

  given defaultByte: Default[Byte] with
    def default: Byte = 0

  given defaultShort: Default[Short] with
    def default: Short = 0

  given defaultInt: Default[Int] with
    def default: Int = 0

  given defaultLong: Default[Long] with
    def default: Long = 0

  given defaultBigInt: Default[BigInt] with
    def default: BigInt = 0

  /*******************************
   * Floating point number types *
   *******************************/

  given defaultFloat: Default[Float] with
    def default: Float = 0.0

  given defaultDouble: Default[Double] with
    def default: Double = 0.0

  given defaultBigDecimal: Default[BigDecimal] with
    def default: BigDecimal = 0.0

  /***********
   * Boolean *
   ***********/

  given defaultBoolean: Default[Boolean] with
    def default: Boolean = false

  /**************************
   * Characters and strings *
   **************************/

  given defaultChar: Default[Char] with
    def default: Char = '\u0000'

  given defaultString: Default[String] with
    def default: String = ""

  /********************
   * Collection types *
   ********************/

  given defaultOption[T]: Default[Option[T]] with
    def default: Option[T] = None

  given defaultList[T]: Default[List[T]] with
    def default: List[T] = Nil

  given defaultLazyList[T]: Default[LazyList[T]] with
    def default = LazyList[T]()

  given defaultImmutableArraySeq[T: ClassTag]: Default[immutable.ArraySeq[T]] with
    def default = immutable.ArraySeq[T]()

  given defaultVector[T]: Default[Vector[T]] with
    def default = Vector[T]()

  given defaultImmutableQueue[T]: Default[immutable.Queue[T]] with
    def default = immutable.Queue[T]()

  given defaultRange: Default[Range] with
    def default: Range = Range(0, 0)

  given defaultArrayBuffer[T]: Default[mutable.ArrayBuffer[T]] with
    def default = mutable.ArrayBuffer[T]()

  given defaultListBuffer[T]: Default[mutable.ListBuffer[T]] with
    def default = mutable.ListBuffer[T]()

  given defaultStringBuilder: Default[StringBuilder] with
    def default = StringBuilder()

  given defaultMutableQueue[T]: Default[mutable.Queue[T]] with
    def default = mutable.Queue[T]()

  given defaultMutableArraySeq[T: ClassTag]: Default[mutable.ArraySeq[T]] with
    def default = mutable.ArraySeq[T]()

  given defaultStack[T]: Default[mutable.Stack[T]] with
    def default = mutable.Stack[T]()

  given defaultArray[T: ClassTag]: Default[Array[T]] with
    def default = Array[T]()

  given defaultArrayDeque[T]: Default[mutable.ArrayDeque[T]] with
    def default = mutable.ArrayDeque[T]()

  given defaultMap[K, V]: Default[mutable.Map[K, V]] with
    def default: mutable.Map[K, V] = mutable.Map[K, V]()

  given defaultImmutableHashSet[T]: Default[immutable.HashSet[T]] with
    def default = immutable.HashSet[T]()

  given defaultImmutableHashMap[K, V]: Default[immutable.HashMap[K, V]] with
    def default = immutable.HashMap[K, V]()

  given defaultImmutableTreeSet[T: Ordering]: Default[immutable.TreeSet[T]] with
    def default = immutable.TreeSet[T]()

  given defaultTreeMap[K: Ordering, V]: Default[immutable.TreeMap[K, V]] with
    def default = immutable.TreeMap[K, V]()

  given defaultImmutableBitSet: Default[immutable.BitSet] with
    def default = immutable.BitSet()

  given defaultVectorMap[K, V]: Default[immutable.VectorMap[K, V]] with
    def default = immutable.VectorMap[K, V]()

  given defaultListMap[K, V]: Default[immutable.ListMap[K, V]] with
    def default = immutable.ListMap[K, V]()

  given defaultMutableHashSet[T]: Default[mutable.HashSet[T]] with
    def default = mutable.HashSet[T]()

  given defaultMutableHashMap[K, V]: Default[mutable.HashMap[K, V]] with
    def default = mutable.HashMap[K, V]()

  given defaultWeakHashMap[K, V]: Default[mutable.WeakHashMap[K, V]] with
    def default = mutable.WeakHashMap[K, V]()

  given defaultMutableBitSet: Default[mutable.BitSet] with
    def default = mutable.BitSet()

  given defaultMutableTreeSet[T: Ordering]: Default[mutable.TreeSet[T]] with
    def default = mutable.TreeSet[T]()
