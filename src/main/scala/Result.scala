import scala.util.{Either, Failure, Left, Right, Success, Try}


/**
 * A type that represents either success ([[Ok]]) or failure ([[Err]]).
 *
 * Inspired by Rust’s `Result` type.
 * Often used for fallible computations where exceptions are avoided.
 */
sealed trait Result[+T, +E]:

  /************************
   * Querying the variant *
   ************************/

  /** Returns true if this is an [[Ok]] value. */
  def isOk: Boolean = this match
    case Ok(_) => true
    case Err(_) => false

  /** Returns true if this is an [[Err]] value. */
  def isErr: Boolean = !isOk

  /**
   * Returns true if this is an [[Ok]] value and the given predicate returns true for the contained value.
   *
   * @param f the predicate to apply to the contained value
   */
  def isOkAnd(f: T => Boolean): Boolean = this match
    case Ok(value) => f(value)
    case _ => false

  /**
   * Returns true if this is an [[Err]] value and the given predicate returns true for the contained error.
   *
   * @param f the predicate to apply to the error value
   */
  def isErrAnd(f: E => Boolean): Boolean = this match
    case Err(error) => f(error)
    case _ => false

  /*******************************
   * Extracting contained values *
   *******************************/

  /**
   * Unwraps the value if this is [[Ok]], or throws an exception with the provided message if this is [[Err]].
   *
   * @param message custom message to throw with exception
   */
  def expect(message: String): T = this match
    case Ok(value) => value
    case Err(_) => throw new NoSuchElementException(message)

  /**
   * Unwraps the error if this is [[Err]], or throws an exception with the provided message if this is [[Ok]].
   *
   * @param message custom message to throw with exception
   */
  def expectErr(message: String): E = this match
    case Err(error) => error
    case Ok(_) => throw new NoSuchElementException(message)

  /** Returns the contained [[Ok]] value, or throws an exception if this is [[Err]]. */
  def unwrap: T = this match
    case Ok(value) => value
    case Err(error) => throw new NoSuchElementException(s"Called unwrap on Err($error)")

  /** Returns the contained [[Err]] error, or throws an exception if this is [[Ok]]. */
  def unwrapErr: E = this match
    case Err(error) => error
    case Ok(value) => throw new NoSuchElementException(s"Called unwrapErr on Ok($value)")

   /**
   * Returns the contained value if this is [[Ok]], or the given default value otherwise.
   *
   * @param default fallback value to use if this is [[Err]]
   */
  def unwrapOr[U >: T](default: => U): U = this match
    case Ok(value) => value
    case Err(error) => default

  /** Returns the contained value if this is [[Ok]], or the default value from the [[Default]] type class. */
  def unwrapOrDefault[U >: T](using d: Default[U]): U = this match
    case Ok(value) => value
    case Err(_) => d.default

  /**
   * Returns the contained value if this is [[Ok]], or computes a fallback value from the error.
   *
   * @param f function to compute a fallback from the error
   */
  def unwrapOrElse[U >: T](f: E => U): U = this match
    case Ok(value) => value
    case Err(error) => f(error)

  /*********************************
   * Transforming contained values *
   *********************************/

  /** Maps the [[Ok]] value using the provided function. */
  def map[U](f: T => U): Result[U, E] = this match
    case Ok(value) => Ok(f(value))
    case Err(error) => Err(error)

  /** Maps the [[Err]] error using the provided function. */
  def mapErr[F](f: E => F): Result[T, F] = this match
    case Ok(value) => Ok(value)
    case Err(error) => Err(f(error))

  /** Applies the function if [[Ok]], or returns the provided default if [[Err]]. */
  def mapOr[U](f: T => U, default: U): U = this match
    case Ok(value) => f(value)
    case Err(_) => default

  /** Applies `f` if [[Ok]], or applies `fallback` if [[Err]]. */
  def mapOrElse[U](f: T => U, fallback: E => U): U = this match
    case Ok(value) => f(value)
    case Err(error) => fallback(error)

  /**
   * Flatten a nested [[Result]] structure.
   *
   * Convert [[Result]] of [[Result]] into a single [[Result]].
   */
  def flatten[U, F >: E](using ev: T <:< Result[U, F]): Result[U, F] = this match
    case Ok(inner) => inner
    case Err(e) => Err(e)

  /** Chains another [[Result]]-producing computation on the [[Ok]] value. */
  def flatMap[U, F >: E](f: T => Result[U, F]): Result[U, F] = this match
    case Ok(value) => f(value)
    case Err(error) => Err(error)

  /** Applies `onOk` if [[Ok]], or `onErr` if [[Err]]. */
  def fold[U](onOk: T => U, onErr: E => U): U = this match
    case Ok(value) => onOk(value)
    case Err(error) => onErr(error)

  /**
   * Filters the value inside [[Ok]] using the given predicate.
   *
   * If the predicate fails, returns a default error.
   */
  def withFilter(p: T => Boolean): Result[T, E] = this match
    case Ok(value) if p(value) => this
    case Ok(_) => Err("Predicate failed").asInstanceOf[Result[T, E]]
    case e @ Err(_) => e

  /*******************************
   * Conversion to similar types *
   *******************************/

  /**
   * Converts this to [[Option]].
   *
   * @return [[Some]] if [[Ok]], [[None]] if [[Err]]
   */
  def toOption: Option[T] = this match
    case Ok(value) => Some(value)
    case Err(_) => None

  /**
   * Converts this to [[Either]].
   *
   * @return [[Right]] if [[Ok]], [[Left]] otherwise
   */
  def toEither: Either[E, T] = this match
    case Ok(value) => Right(value)
    case Err(error) => Left(error)


/** Companion object for the [[Result]] type. */
object Result:

  /**
   * Converts a [[Try]] to a [[Result]].
   *
   * @param t the [[Try]] to convert
   * @return [[Ok]] if [[Success]], [[Err]] otherwise
   */
  def fromTry[T](t: Try[T]): Result[T, Throwable] = t match
    case Success(value) => Ok(value)
    case Failure(error) => Err(error)


/**  A successful [[Result]] containing a value of type `T`. */
case class Ok[+T](value: T) extends Result[T, Nothing]


/** A failed [[Result]] containing an error value of type `E`. */
case class Err[+E](error: E) extends Result[Nothing, E]
