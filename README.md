# Cipher Challenge 2024

Cipher challenge 2024 repository by Cambridge Maths School students.

Deciphered Story: [Submissions](submissions/Submissions.md)  
Challenge 10B Submission: [AESAlchemists10BSubmission](postchallenge/AESAlchemists10BSubmission.pdf)

## Ciphers Written:
- [Affine Cipher](src/main/java/main/ciphers/monoalphabetic/AffineCipher.java)
- [Beaufort Cipher](./src/main/scala/com/core/ciphers/BeaufortCipher.scala)
- [Caesar Cipher](./src/main/scala/com/core/ciphers/CaesarCipher.scala)
- [Columnar Transposition Cipher](./src/main/scala/com/core/ciphers/ColumnCipher.scala)
- [Four Square Cipher](./src/main/scala/com/core/ciphers/FourSquareCipher.scala)
- [Hill Cipher](./src/main/scala/com/core/ciphers/HillCipher.scala)
- [Keyword Substitution Cipher](src/main/java/main/ciphers/monoalphabetic/KeywordSubstitutionCipher.java)
- [Matrix Transposition Cipher](src/main/java/main/ciphers/transposition/MatrixTranspositionCipher.java)
- [Mono-Alphabetic Substitution Cipher](./src/main/scala/com/core/ciphers/MonoAlphabeticSubstitutionCipher.scala)
- [Periodic Affine Cipher](src/main/java/main/ciphers/periodicpolyalphabetic/PeriodicAffineCipher.java)
- [Periodic Poly-alphabetic Substitution Cipher](src/main/java/main/ciphers/periodicpolyalphabetic/PeriodicPolyAlphabeticSubstitutionCipher.java)
- [Permutation Cipher](./src/main/scala/com/core/ciphers/PermutationCipher.scala)
- [Polybius Square Cipher](./src/main/scala/com/core/ciphers/PolybiusCipher.scala)
- [Porta Cipher](src/main/java/main/ciphers/periodicpolyalphabetic/PortaCipher.java)
- [Quagmire1 Cipher](src/main/java/main/ciphers/periodicpolyalphabetic/Quagmire1Cipher.java)
- [Quagmire2 Cipher](src/main/java/main/ciphers/periodicpolyalphabetic/Quagmire2Cipher.java)
- [Quagmire3 Cipher](src/main/java/main/ciphers/periodicpolyalphabetic/Quagmire3Cipher.java)
- [Quagmire4 Cipher](src/main/java/main/ciphers/periodicpolyalphabetic/Quagmire4Cipher.java)
- [Variant Beaufort Cipher](src/main/java/main/ciphers/periodicpolyalphabetic/VariantBeaufortCipher.java)
- [Vigenère Cipher](./src/main/scala/com/core/ciphers/VigenereCipher.scala)

## Breakers Written:
- [Affine Breaker](src/main/java/main/breakers/monoalphabetic/AffineCipherBreaker.java)
- [Beaufort Breaker](src/main/java/main/breakers/periodicpolyalphabetic/BeaufortCipherBreaker.java)
- [Caesar Breaker](./src/main/scala/com/core/cipherbreakers/CaesarCipherBreaker.scala)
- [Keyword Substitution Breaker](src/main/java/main/breakers/monoalphabetic/KeywordSubstitutionCipherBreaker.java)
- [Matrix Transposition Breaker](src/main/java/main/breakers/transposition/MatrixTranspositionCipherBreaker.java)
- [Mono-Alphabetic Substitution Breaker](./src/main/scala/com/core/cipherbreakers/MonoAlphabeticSubstitutionCipherBreaker.scala)
- [Periodic Affine Breaker](src/main/java/main/breakers/periodicpolyalphabetic/PeriodicAffineCipherBreaker.java)
- [Periodic Poly-alphabetic Substitution Breaker](src/main/java/main/breakers/periodicpolyalphabetic/PeriodicPolyAlphabeticSubstitutionCipherBreaker.java)
- [Permutation Breaker](/src/main/scala/com/core/cipherbreakers/PermutationCipherBreaker.scala)
- [Porta Breaker](src/main/java/main/breakers/periodicpolyalphabetic/PortaCipherBreaker.java)
- [Quagmire1 Breaker](src/main/java/main/breakers/periodicpolyalphabetic/Quagmire1CipherBreaker.java)
- [Quagmire2 Breaker](src/main/java/main/breakers/periodicpolyalphabetic/Quagmire2CipherBreaker.java)
- [Quagmire3 Breaker](src/main/java/main/breakers/periodicpolyalphabetic/Quagmire3CipherBreaker.java)
- [Quagmire4 Breaker](src/main/java/main/breakers/periodicpolyalphabetic/Quagmire4CipherBreaker.java)
- [Vigenère Breaker](./src/main/scala/com/core/cipherbreakers/VigenereCipherBreaker.scala)
- [Variant Beaufort Breaker](src/main/java/main/breakers/periodicpolyalphabetic/VariantBeaufortCipherBreaker.java)

## Getting Started

1. Download a Java Development Kit (JDK) from [Oracle](https://www.oracle.com/uk/java/technologies/downloads/). Version 21 is the latest version supported by Scala at the time of writing.
2. Download and install [Scala](https://www.scala-lang.org/download/).
3. In Visual Studio Code, install the Scala (Metals) and Code Runner extensions.
4. You should be able to run any file with the `.scala` extension by pressing `Ctrl + F5`.

## Why Scala?

Scala is a statically-typed language that runs on the Java Virtual Machine (JVM). It's fast, functional and object-oriented, which makes it ideal for manipulating cipher data.

## Project Structure

#### Structure

- Shared code across challenges: [src/main/scala/com/core/](./src/main/scala/com/core/)
- Unit tests: [src/test/scala/com/](./src/test/scala/com)
- Data, texts and tables: [resources/](./resources/)
- Submissions: [submissions/](./submissions/)

#### Individual Code and Other Languages
 If you're writing personal code in Scala or Java, it goes in [src/main/scala/com/team/\<yourname\>](./src/main/scala/com/team/). This allows Java and Scala developers to share code. Any code that will not use the pre-existing Scala libraries can go in [src/main/\<language\>/team/\<yourname\>/](./src/main/).

## Scala Code Standards
 `.scalafmt.conf` is used to enforce code standards. The metals extension can automatically format your code in Visual Studio Code with `Shift + Alt + F`.
 - **Indentation**: Use 4 spaces.
 - **Naming**:
   - Classes and objects: `PascalCase`
   - Methods and variables: `camelCase`
   - Constants: `UPPER_SNAKE_CASE`
   - Folders and packages: `lowercaseconcatenation`
   - Files: `PascalCase`
 - **Comments**: Use them, and make the most of docstrings. "Intuition" is unfortunately not always possible.
 - **Unit Tests**: They are your friends. Aim to have anything in `core` covered. Visual Studio Code has good support for tests.
 - **Imports**: Third-party imports should go before project imports.
 - **Error Handling**: Handle errors where possible. If for performance reasons you've decided not to, please document the possible error and how to avoid it.
