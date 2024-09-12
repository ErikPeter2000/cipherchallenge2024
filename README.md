# Cipher Challenge 2024

Cipher challenge 2024 repository by Cambridge Maths School students, written in Scala.

## Ciphers Written:
 - [Beaufort Cipher](./src/main/scala/com/core/ciphers/BeaufortCipher.scala)
 - [Caesar Cipher](./src/main/scala/com/core/ciphers/CaesarCipher.scala)
 - [Columnar Transposition](./src/main/scala/com/core/ciphers/ColumnCipher.scala)
 - [Four Square Cipher](./src/main/scala/com/core/ciphers/FourSquareCipher.scala)
 - [Hill Cipher](./src/main/scala/com/core/ciphers/HillCipher.scala)
 - [Polybius Square](./src/main/scala/com/core/ciphers/PolybiusCipher.scala)
 - [Substitution Cipher](./src/main/scala/com/core/ciphers/SubstitutionCipher.scala)
 - [Transposition Cipher](./src/main/scala/com/core/ciphers/TranspositionCipher.scala)
 - [Vigenère Cipher](./src/main/scala/com/core/ciphers/VigenereCipher.scala)

## Breakers Written:
- [Caesar Breaker](./src/main/scala/com/core/breakerpresets/CaesarCipherBreaker.scala)
- [Substitution Breaker](./src/main/scala/com/core/breakerpresets/SubstitutionCipherBreaker.scala)
- [Transposition Breaker](./src/main/scala/com/core/breakerpresets/TranspositionCipherBreaker.scala)
- [Vigenère Breaker](./src/main/scala/com/core/breakerpresets/VigenereCipherBreaker.scala)

## Getting Started

1. Download a Java Development Kit (JDK) from [Oracle](https://www.oracle.com/uk/java/technologies/downloads/). Version 21 is the latest version supported by Scala.
2. Download and install [Scala](https://www.scala-lang.org/download/).
3. In VSCode, install the Scala (Metals) and Code Runner extensions.
4. You should be able to run any file with the `.scala` extension by pressing `Ctrl + F5`.

## Why Scala?

Scala is a statically-typed language that runs on the Java Virtual Machine (JVM). It's fast, functional and object-oriented, which makes it ideal for manipulating cipher data.

## Project Structure

 - Shared code across challenges: [src/main/scala/com/core](./src/main/scala/com/core/)
 - Challenge solutions: [src/main/scala/com/challenges](./src/main/scala/com/challenges/)
 - Code for individual team members: [src/main/scala/com/team](./src/main/scala/com/team/)
 - Unit tests: [src/test/scala/com](./src/test/scala/com)
 - Data, texts and tables: [resources/](./resources/)

## Code Standards
 `.scalafmt.conf` is used to enforce code standards. The metals extension can automatically format your code in VSCode with `Shift + Alt + F`.
 - **Indentation**: Use 4 spaces.
 - **Naming**:
   - Classes and objects: `PascalCase`
   - Methods and variables: `camelCase`
   - Constants: `UPPER_SNAKE_CASE`
   - Folders and packages: `lowercaseconcatenation`
   - Files: `PascalCase`
   - Test files: `PascalCaseTests`
 - **Comments**: Use them, and make the most of docstrings. "Intuition" is unfortunately not always possible.
 - **Unit Tests**: They are your friends, and AI does a good job writing the structure for them. Aim to have anything in `core` covered. VSCode has good support for tests.
 - **Imports**: Third-party imports should go before project imports.
 - **Error Handling**: Add comments explaining what error could be thrown, but handling is not required.