# Scala Introduction

This document is a brief introduction to Scala, a statically-typed language that runs on the Java Virtual Machine (JVM). It's fast, functional and object-oriented, which makes it ideal for manipulating cipher data.

The syntax is similar to most curly-brace languages, but with a few syntactic shortcuts in places. Those familiar with Java or JavaScript should find it "easy" to pick up.

## Code Entry

The easiest way to run code in VSCode is to use the Code Runner extension. You can run the current file by pressing `Ctrl + F5`.

You should put your working code in the `src/main/scala/com/team/` directory in a subfolder with your name. 

The main file should be named `Main.scala` and contain a `main` method in a `Main` object.

```scala
// src/main/scala/com/team/johndoe/Main.scala
package com.team.johndoe

object Main {
  def main(args: Array[String]): Unit = {
    println("Hello, World!")
  }
}
```

## Variable Declaration

Scala has two types of variables: `val` and `var`. `val` is immutable, while `var` is mutable. It's good practice to use `val` wherever possible.

You will also need to declare the type of the variable, but Scala has type inference, so you can often leave it out.

```scala
val x: Int = 10
var y: String = "Hello, World!"

x = 20 // This will throw an error
y = "Goodbye, World!" // This is fine

val z = 10 // Type inference for Int
val w = 10.0 // Type inference for Double
```

## Function Declaration

Functions are declared using the `def` keyword. You can also use type inference for the return type.

```scala
def add(x: Int, y: Int): Int = {
  return x + y
}

def method(message: string): Unit = { // Unit is equivalent to void, or no return type
  println(message)
}

def multiply(x: Int, y: Int) = x * y // Type inference for return type

def divide(x: Int, y: Int): Double = {
  if (y == 0) {
    throw new IllegalArgumentException("Cannot divide by zero")
  }
  x / y // No return keyword needed. The last line can usually be inferred as the return value
}
```

## Using Scala's Functions

Scala has a lot of in-built functions that can be used to manipulate data. Since Scala is functional, you can chain these functions together to create complex transformations.

```scala
val listOfLists = List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))

val sum = listOfLists.flatten.sum // 45
val pairs = listOfLists.flatten.grouped(2).toList // List(List(1, 2), List(3, 4), List(5, 6), List(7, 8), List(9))
val transposed = listOfLists.transpose // List(List(1, 4, 7), List(2, 5, 8), List(3, 6, 9))
```

## Collections

Scala has many collections, which is one reason I like the language. The base collection type is `Seq`, which is a sequence of elements. You can use `List`, `Vector`, `Array`, `Set` and `Map` as well. Collections are immutable by default. To use mutable collections, you need to import them from `scala.collection.mutable`. Notice how most collections are not instantiated with the `new` keyword, and instead use a factory method.

```scala

val list = List(1, 2, 3, 4, 5) // Linked list. 
val vector = Vector(1, 2, 3, 4, 5) // Indexed sequence. Fast lookups.
val array = Array(1, 2, 3, 4, 5) // Mutable sequence. Fixed size.
val set = Set(1, 2, 3, 4, 5) // Unordered collection of unique elements.
val map = Map("one" -> 1, "two" -> 2, "three" -> 3) // Key-value pairs.

val listWithPrepend = 0 +: list // [0, 1, 2, 3, 4, 5]
val listWithAppend = list :+ 6 // [1, 2, 3, 4, 5, 6]

val mutableList = scala.collection.mutable.ListBuffer(1, 2, 3, 4, 5)
mutableList.append(6) // [1, 2, 3, 4, 5, 6]
mutableList += 7 // [1, 2, 3, 4, 5, 6, 7]
mutableList ++= List(8, 9, 10) // Concatenation: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
```

## Classes and Objects

Scala is an object-oriented language, so you can define classes and objects. Classes are blueprints for objects, while objects are singletons (similar to "static" classes).

```scala
import scala.compiletime.uninitialized
import scala.collection.mutable.ListBuffer

class Person(name: String, age: Int) {
  def greet(): Unit = {
    println(s"Hello, my name is $name and I am $age years old.") // String interpolation
  }
}

object Earth {
  val gravity = 9.8
  val people = ListBuffer(new Person("John", 25), new Person("Jane", 30)) // Mutable list. Instantiation.
}

object Main {
  def main(args: Array[String]): Unit = {
    Earth.people.foreach(_.greet()) // Iteration over a list, using a lambda function to call greet() for each person
    val person = new Person("Alice", 20)
    Earth.people += person
  }
}
```

## Useful Methods

```scala
// Sequence methods
val collection = Seq(1, 2, 3, 4, 5, 6, 7, 8, 9)
// Map
val doubled = collection.map(_ * 2) // [2, 4, 6, 8, 10, 12, 14, 16, 18]
// Filter
val evens = collection.filter(_ % 2 == 0) // [2, 4, 6, 8]
// Reduce
val sum = collection.reduce(_ + _) // 45
// Fold. Like reduce, but with an initial value
val sumWithInitial = collection.fold(0)(_ + _) // 45
// Find. Returns the first element that satisfies the predicate
val firstEven = collection.find(_ % 2 == 0) // Some(2)
// ForEach. Applies a function to each element
collection.foreach(println) // Prints each element
collection.foreach { element =>
    if (element % 2 == 0) {
        println(element)
    } else {
        println("Odd")
    }
}
// other
val reversed = collection.reverse // [9, 8, 7, 6, 5, 4, 3, 2, 1]
val sorted = reversed.sorted // [1, 2, 3, 4, 5, 6, 7, 8, 9]
val collectionWithDuplicates = Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9)
val distinct = collectionWithDuplicates.distinct // [1, 2, 3, 4, 5, 6, 7, 8, 9]
val sum = collection.sum // 45
val max = collection.max // 9
val min = collection.min // 1
```

## Using the Repository Library

The repository is designed to contain shared code to be reused across challenges. 

```scala
// for IO
import scala.io.Source
import java.nio.file.Paths

// for the repository
import com.core.alphabets._
import com.core.ciphers._
import com.core.cipherdata.CipherDataBlock

object Main {
    /**
     * Loads the plaintext from an example file
     */    
    def loadData(): CipherDataBlock[Char] = {
        val path = Paths.get(".\\resources\\text\\DostoevskyCrimeAndPunishment.txt")
        val text = Source.fromFile(path.toFile, "UTF-8").take(2000).mkString.toUpperCase.replaceAll("[^A-Z]", "") // Take first 2000 chars, then remove non-alphabetic
        CipherDataBlock.create(text) // return a CipherDataBlock for use in the ciphers
    }

    /**
     * Main method
     */
    def main(args: Array[String]): Unit = {
        val plainText = loadData() // Load the data
        val encrypted = CaesarCipher.encrypt(plainText, 3).outData // Encrypt with Caesar cipher, shift 3. Get the cipher text from the result of the cipher using `outData`
        println(encrypted.mkString)
        val decrypted = CaesarCipher.decrypt(encrypted, 3).outData // Decrypt in a similar way.
        println(decrypted.mkString)
    }
}
```