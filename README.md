# Cipher Challenge 2024

Cipher challenge 2024 repository by Cambridge Maths School students, originally intended to be written in Scala.

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
 - [Affine Cipher](src/main/java/unyxe/main/ciphers/AffineCipher.java)
 - [Keyword Substitution Cipher](src/main/java/unyxe/main/ciphers/KeywordSubstitutionCipher.java)
 - [Periodic Poly-alphabetic Substitution](src/main/java/unyxe/main/ciphers/PeriodicPolyAlphabeticSubstitution.java)
 - [Porta](src/main/java/unyxe/main/ciphers/PortaCipher.java)
 - [Quagmire1](src/main/java/unyxe/main/ciphers/Quagmire1Cipher.java)

## Breakers Written:
- [Caesar Breaker](./src/main/scala/com/core/cipherbreakers/CaesarCipherBreaker.scala)
- [Substitution Breaker](./src/main/scala/com/core/cipherbreakers/SubstitutionCipherBreaker.scala)
- [Transposition Breaker](./src/main/scala/com/core/cipherbreakers/TranspositionCipherBreaker.scala)
- [Vigenère Breaker](./src/main/scala/com/core/cipherbreakers/VigenereCipherBreaker.scala)
- [Beaufort Breaker](src/main/java/unyxe/main/breakers/BeaufortCipherBreaker.java)
- [Affine Breaker](src/main/java/unyxe/main/breakers/AffineCipherBreaker.java)
- [Keyword Substitution Breaker](src/main/java/unyxe/main/breakers/KeywordSubstitutionCipherBreaker.java)
- [Porta](src/main/java/unyxe/main/breakers/PortaCipherBreaker.java)
- [Quagmire1](src/main/java/unyxe/main/breakers/Quagmire1CipherBreaker.java)

## Getting Started

1. Download a Java Development Kit (JDK) from [Oracle](https://www.oracle.com/uk/java/technologies/downloads/). Version 21 is the latest version supported by Scala.
2. Download and install [Scala](https://www.scala-lang.org/download/).
3. In VSCode, install the Scala (Metals) and Code Runner extensions.
4. You should be able to run any file with the `.scala` extension by pressing `Ctrl + F5`.

## Why Scala?

Scala is a statically-typed language that runs on the Java Virtual Machine (JVM). It's fast, functional and object-oriented, which makes it ideal for manipulating cipher data.

## Project Structure

#### Structure

- Shared code across challenges: [src/main/scala/com/core](./src/main/scala/com/core/)
- Saved challenge solutions: [src/main/scala/com/challenges/](./src/main/scala/com/challenges/)
- Scala or Java code for individual team members: [src/main/scala/com/team/](./src/main/scala/com/team/)
- Unit tests: [src/test/scala/com/](./src/test/scala/com)
- Data, texts and tables: [resources/](./resources/)
- Submissions: [submissions/](./submissions/)

#### Individual Code and Other Languages
 If you're writing personal code in Scala or Java, it goes in [src/main/scala/com/team/\<yourname\>](./src/main/scala/com/team/). This allows Java and Scala developers to share code. Any code that will not use the pre-existing Scala libraries can go in [src/main/\<language\>/team/\<yourname\>/](./src/main/).

## Scala Code Standards
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
