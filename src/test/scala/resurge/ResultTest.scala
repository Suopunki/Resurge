package resurge

import org.scalatest.funspec.AnyFunSpec

import scala.util.{Failure, Success}

class ResultTest extends AnyFunSpec:
  describe("Querying the variant"):
    it("Ok isOk should return true"):
      assert(Ok(42).isOk)

    it("Err isOk should return false"):
      assert(!Err("Error").isOk)

    it("Ok isErr should return false"):
      assert(!Ok(42).isErr)

    it("Err isErr should return true"):
      assert(Err("Error").isErr)

    it("Ok isOkAnd should return true for matching predicate"):
      assert(Ok(42).isOkAnd(_ > 40))

    it("Ok isOkAnd should return false for non-matching predicate"):
      assert(!Ok(42).isOkAnd(_ < 40))

    it("Err isOkAnd should return false"):
      assert(!Err("Error").isOkAnd(_ => true))

    it("Ok isErrAnd should return false"):
      assert(!Ok(42).isErrAnd(_ => true))

    it("Err isErrAnd should return true for matching predicate"):
      assert(Err("Error").isErrAnd(_ == "Error"))

    it("Err isErrAnd should return false for non-matching predicate"):
      assert(!Err("Error").isErrAnd(_ == "Other"))

  describe("Extracting contained values"):
    it("Ok expect should return the contained value"):
      assert(Ok(42).expect("Should not fail") == 42)

    it("Err expect should throw exception"):
      intercept[NoSuchElementException]:
        Err("Error").expect("Should throw exception")

    it("Ok expectErr should throw exception"):
      intercept[NoSuchElementException]:
        Ok(42).expectErr("Should throw exception")

    it("Err expectErr should return the contained error"):
      assert(Err("Error").expectErr("Should not fail") == "Error")

    it("Ok unwrap should return the contained value"):
      assert(Ok(42).unwrap == 42)

    it("Err unwrap should throw exception"):
      intercept[NoSuchElementException]:
        Err("Error").unwrap

    it("Ok unwrapErr should throw exception"):
      intercept[NoSuchElementException]:
        Ok(42).unwrapErr

    it("Err unwrapErr should return the contained error"):
      assert(Err("Error").unwrapErr == "Error")

    it("Ok unwrapOr should return the contained value"):
      assert(Ok(42).unwrapOr(0) == 42)

    it("Err unwrapOr should return the default value"):
      assert(Err("Error").unwrapOr(0) == 0)

    it("Ok unwrapOrDefault should return the contained value"):
      assert(Ok(42).unwrapOrDefault(using new Default[Int] { def default: Int = 100 }) == 42)

    it("Err unwrapOrDefault should return the default value"):
      assert(Err("Error").unwrapOrDefault(using new Default[Int] { def default: Int = 100 }) == 100)

    it("Ok unwrapOrElse should return the contained value"):
      assert(Ok(42).unwrapOrElse(_ => 0) == 42)

    it("Err unwrapOrElse should return the fallback value from function"):
      assert(Err("Error").unwrapOrElse(_ => 100) == 100)

  describe("Transforming contained values"):
    it("Ok map should transform the contained value"):
      assert(Ok(42).map(_ * 2) == Ok(84))

    it("Err map should not transform the value"):
      assert(Err("Error").map((x: Int) => x * 2) == Err("Error"))

    it("Ok mapErr should not transform the error"):
      assert(Ok(42).mapErr((err: String) => s"Mapped: $err") == Ok(42))

    it("Err mapErr should transform the error"):
      assert(Err("Error").mapErr((err: String) => s"Mapped: $err") == Err("Mapped: Error"))

    it("Ok mapOr should transform the value if Ok"):
      assert(Ok(42).mapOr((x: Int) => x * 2, 0) == 84)

    it("Err mapOr should return default value"):
      assert(Err("Error").mapOr((x: Int) => x * 2, 0) == 0)

    it("Ok mapOrElse should transform the value if Ok"):
      assert(Ok(42).mapOrElse((x: Int) => x * 2, (_: String) => 0) == 84)

    it("Err mapOrElse should transform the error if Err"):
      assert(Err("Error").mapOrElse((x: Int) => x * 2, (err: String) => err) == "Error")

    it("Ok flatten should unwrap nested Ok result"):
      assert(Ok(Ok(42)).flatten == Ok(42))

    it("Err flatten should propagate the Err"):
      assert(Err("Error").flatten == Err("Error"))

    it("Ok flatMap should chain computations"):
      assert(Ok(42).flatMap((x: Int) => Ok(x * 2)) == Ok(84))

    it("Err flatMap should propagate the error"):
      assert(Err("Error").flatMap((x: Int) => Ok(x * 2)) == Err("Error"))

    it("Err should fold correctly"):
      assert(Err("Error").fold((x: Int) => x * 2, (err: String) => s"Error: $err") == "Error: Error")

    it("Ok should fold correctly"):
      assert(Ok(42).fold((x: Int) => x * 2, (_: String) => 0) == 84)

    it("Ok withFilter should return Ok when predicate matches"):
      assert(Ok(42).withFilter(_ > 40)(()) == Ok(42))

    it("Err withFilter should return Err"):
      assert(Err("Error").withFilter(_ => true)(()) == Err("Error"))

    it("Ok with failed predicate should return Err"):
      assert(Ok(42).withFilter(_ < 40)("Predicate failed") == Err("Predicate failed"))

  describe("Conversion to"):
    it("Ok should correctly convert to Option"):
      assert(Ok(42).toOption == Some(42))

    it("Err should convert to None in Option"):
      assert(Err("Error").toOption.isEmpty)

    it("Ok should convert to Right in Either"):
      assert(Ok(42).toEither == Right(42))

    it("Err should convert to Left in Either"):
      assert(Err("Error").toEither == Left("Error"))

  describe("Conversion from"):
    it("Ok fromTry should map Success correctly"):
      assert(Result.fromTry(Success(42)) == Ok(42))

    it("Err fromTry should map Failure correctly"):
      Result.fromTry(Failure(RuntimeException("Error"))) match
        case Err(e: RuntimeException) => assert(e.getMessage == "Error")
        case _ => fail("Expected Err with RuntimeException")
