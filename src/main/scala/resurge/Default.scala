package resurge

/** Type class representing types that can provide a default value. */
trait Default[T]:
  /** The default value for a given type. */
  def default: T


/** Companion object for the [[Default]] type. */
object Default:
  /** Used for getting the default value of a type. */
  def apply[T](using d: Default[T]): Default[T] = d
