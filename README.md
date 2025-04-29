# Resurge - A Rust-inspired result type for Scala

![Scala version](https://img.shields.io/badge/Scala-3.3.5-db2f2f?&logo=scala&logoColor=db2f2f)
![sbt version](https://img.shields.io/badge/sbt-1.10.11-white)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

`Resurge` provides a simple, Rust-inspired `Result` type for Scala, designed to handle success and failure outcomes in computations, avoiding exceptions.
It models computations that can either succeed with a value (`Ok`) or fail with an error (`Err`).

This library is not yet published to public repositories like Maven Central, as it currently lacks extensive real-world usage and testing to ensure production readiness.
However, it can be built and published locally for personal or internal use.

## Table of contents

- [Features](#features)
- [Installation](#installation)
  - [Cloning this repository](#1-clone-this-repository)
  - [Publishing to your local repository](#2-publish-to-your-local-repository)
  - [Adding the dependency](#3-add-the-dependency-to-your-buildsbt-file)
- [Usage](#usage)
  - [Result instance methods](#result-instance-methods)
    - [Querying the variant](#querying-the-variant)
    - [Extracting contained values](#extracting-contained-values)
    - [Transforming contained values](#transforming-contained-values)
    - [Conversion to similar types](#conversion-to-similar-types)
  - [Result companion object methods](#result-companion-object-methods)
    - [Conversion from similar types](#conversion-from-similar-types)
  - [Declaring default values for types](#declaring-default-values-for-types)
  - [Examples](#examples)
    - [Handling with pattern matching](#handling-with-pattern-matching)
    - [Querying the variant](#querying-the-variant-1)
    - [Extracting contained values](#extracting-contained-values-1)
    - [Transforming contained values](#transforming-contained-values-1)
    - [Conversion to similar types](#conversion-to-similar-types-1)
    - [Conversion from similar types](#conversion-from-similar-types-1)
- [Development](#development)
  - [Cloning the repository](#1-clone-the-repository)
  - [Running tests](#2-run-tests)
  - [Publishing locally](#3-publish-locally-for-your-own-use)
  - [Making changes](#4-make-changes)
- [License](#license)

## Features

- `Ok[T]`: Represents successful results, holding a value of type `T`.
- `Err[E]`: Represents failure, holding an error of type `E`.
- Error handling: Easily manage fallible operations without exceptions.
- Flexible APIs: Includes methods like `map`, `flatMap`, `fold`, `unwrapOrElse`, and more.
- Inspired by Rust: Provides a familiar structure for developers coming from Rust.

## Installation

You can use this library in your Scala project by publishing it locally using `sbt`.

### 1. Clone this repository

```bash
git clone https://github.com/Suopunki/Resurge
cd resurge
```

### 2. Publish to your local repository

```bash
sbt publishLocal
```

### 3. Add the dependency to your `build.sbt` file

```bash
libraryDependencies += "resurge" %% "resurge" % "0.1.0"
```

## Usage

This guide covers all methods available in the current API version (0.1.0).

### `Result` instance methods

#### Querying the variant

- `isOk`
- `isErr`
- `isOkAnd`
- `isErrAnd`

#### Extracting contained values

- `expect`
- `expectErr`
- `unwrap`
- `unwrapErr`
- `unwrapOr`
- `unwrapOrDefault`
- `unwrapOrElse`

#### Transforming contained values

- `map`
- `mapErr`
- `mapOr`
- `mapOrElse`
- `flatten`
- `flatMap`
- `withFilter`

#### Conversion to similar types

- `toOption`
- `toEither`

### `Result` companion object methods

#### Conversion from similar types

- `fromTry`

### Declaring default values for types

To use default values for your types when calling methods like `unwrapOrDefault`,
you need to declare implicit `Default[T]` instances for those types.

Here's an example for a simple case class:

```scala 3
case class Person(name: String, age: Int)

given Default[Person] with
  def default: Person = Person("", 0)
  
val unwrappingDefault = Err("Failure").unwrapOrDefault // Person("", 0)
```

### Examples

Here are examples of how to use the different `Result` methods.

#### Handling with pattern matching

```scala 3
import resurge.*

def handleAndPrintResult[T, E](result: Result[T, E]): Unit = result match
  case Ok(value)  => println(s"Success with value: $value")
  case Err(error) => println(s"Failure with error: $error")
```

#### Querying the variant

```scala 3
import resurge.*

@main def example(): Unit =
  val successResult = Ok(42)
  val failureResult = Err("Something went wrong")
  
  // isOk(): Boolean
  val isSuccessOk = successResult.isOk() // true
  val isFailureOk = failureResult.isOk() // false
  
  // isErr(): Boolean
  val isSuccessErr = successResult.isErr() // false
  val isFailureErr = failureResult.isErr() // true
  
  // isOkAnd(f: T => Boolean): Boolean
  val isSuccessOkAndTrue = successResult.isOkAnd(_ => true)   // true
  val isSuccessOkAndFalse = successResult.isOkAnd(_ => false) // false
  val isFailureOkAndTrue = failureResult.isOkAnd(_ => true)   // false
  val isFailureOkAndFalse = failureResult.isOkAnd(_ => false) // false
  
  // isErrAnd(f: E => Boolean): Boolean
  val isSuccessErrAndTrue = successResult.isErrAnd(_ => true)   // false
  val isSuccessErrAndFalse = successResult.isErrAnd(_ => false) // false
  val isFailureErrAndTrue = failureResult.isErrAnd(_ => true)   // true
  val isFailureErrAndFalse = failureResult.isErrAnd(_ => false) // false
```

#### Extracting contained values

```scala 3
import resurge.*

@main def example(): Unit =
  val successResult = Ok(42)
  val failureResult = Err("Something went wrong")
  
  // expect(message: String): T
  val value1 = successResult.expect("Expected a value")    // returns 42
  // val value2 = failureResult.expect("Expected a value") // throws an exception with the message
  
  // expectErr(message: String): E
  val error1 = failureResult.expectErr("Expected an error")    // returns "Something went wrong"
  // val error2 = successResult.expectErr("Expected an error") // throws an exception  
  
  // unwrap: T
  val unwrappedSuccess = successResult.unwrap    // returns 42
  // val unwrappedFailure = failureResult.unwrap // throws an exception
  
  // unwrapErr: E
  val unwrappedError = failureResult.unwrapErr         // returns "Something went wrong"
  // val unwrappedSuccessErr = successResult.unwrapErr // throws an exception
  
  // unwrapOr[U >: T](default: => U): U
  val valueOrDefault = successResult.unwrapOr(100)   // 42
  val failureOrDefault = failureResult.unwrapOr(100) // 100
  
  // unwrapOrDefault[U >: T](using d: Default[U]): U
  // Assuming you have Default[T] instances defined
  given Default[Int] with
    def default: Int = 99
  val valueOrProvidedDefault = successResult.unwrapOrDefault   // 42
  val failureOrProvidedDefault = failureResult.unwrapOrDefault // 99
  
  // unwrapOrElse[U >: T](f: E => U): U
  // The fallback function is only evaluated if the result is Err
  val valueOrElse = successResult.unwrapOrElse(err => err.length)   // 42
  val failureOrElse = failureResult.unwrapOrElse(err => err.length) // 20 ("Something went wrong".length)
```

#### Transforming contained values

```scala 3
import resurge.*

@main def example(): Unit =
  val successResult = Ok(42)
  val failureResult = Err("Something went wrong")
  
  // map[U](f: T => U): Result[U, E]
  val mappedSuccess = successResult.map(_ * 2)    // Ok(84)
  val mappedFailure = failureResult.map(_ => 100) // Err("Something went wrong")
  
  // mapErr[F](f: E => F): Result[T, F]
  val mappedErrorSuccess = successResult.mapErr(_.toUpperCase) // Ok(42)
  val mappedErrorFailure = failureResult.mapErr(_.toUpperCase) // Err("SOMETHING WENT WRONG")
  
  // mapOr[U](f: T => U, default: U): U
  val mapOrSuccess = successResult.mapOr(_ * 2, 0)   // 84
  val mapOrFailure = failureResult.mapOr(_ => 0, -1) // -1
  
  // mapOrElse[U](f: T => U, fallback: E => U): U
  val mapOrElseSuccess = successResult.mapOrElse(_ * 2, _.length)  // 84
  val mapOrElseFailure = failureResult.mapOrElse(_ => 0, _.length) // 20
  
  // flatten[U, F >: E](using ev: T <:< Result[U, F]): Result[U, F]
  val nestedOk: Result[Result[Int, String], String] = Ok(Ok(100))
  val flattenedOk = nestedOk.flatten // Ok(100)
  
  // flatMap[U, F >: E](f: T => Result[U, F]): Result[U, F]
  val flatMappedSuccess = successResult.flatMap(v => Ok(v * 2))    // Ok(84)
  val flatMappedFailure = failureResult.flatMap(v => Ok(v.length)) // Err("Something went wrong")
  
  // fold[U](onOk: T => U, onErr: E => U): U
  val foldedSuccess = successResult.fold(v => s"Value: $v", e => s"Error: $e") // "Value: 42"
  val foldedFailure = failureResult.fold(v => s"Value: $v", e => s"Error: $e") // "Error: Something went wrong"
  
  // withFilter[EE >: E](p: T => Boolean)(orElse: => EE): Result[T, EE]
  val filteredTrueSuccess = successResult.withFilter(_ > 40)("Value too small")   // Ok(42)
  val filteredFalseSuccess = successResult.withFilter(_ > 100)("Value too small") // Err("Value too small")
  val filteredFailure = failureResult.withFilter(_ > 100)("Value too small")      // Err("Something went wrong")
```

#### Conversion to similar types

```scala 3
import resurge.*

@main def example(): Unit =
  val successResult = Ok(42)
  val failureResult = Err("Something went wrong")
  
  // toOption: Option[T]
  val successToOption = successResult.toOption // Some(42)
  val failureToOption = failureResult.toOption // None
  
  // toEither: Either[E, T]
  val successToEither = successResult.toEither // Right(42)
  val failureToEither = failureResult.toEither // Left("Something went wrong")
```

#### Conversion from similar types

```scala 3
import scala.util.{Failure, Success, Try}

import resurge.*

@main def example(): Unit =
  // fromTry[T](t: Try[T]): Result[T, Throwable]
  val successTry = Success(10)
  val failureTry = Failure(new RuntimeException("Boom!"))
  val fromSuccessTry = Result.fromTry(successTry) // Ok(10)
  val fromFailureTry = Result.fromTry(failureTry) // Err(RuntimeException("Boom!"))
```

## Development

If you would like to contribute or modify the library:

### 1. Clone the repository

```bash
git clone https://github.com/Suopunki/Resurge
cd resurge
```

### 2. Run tests

```bash
sbt test
```

### 3. Publish locally (for your own use)

```bash
sbt publishLocal
```

### 4. Make changes

Modify the code and update tests as needed.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
