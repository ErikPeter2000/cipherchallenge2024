val ciphertext = """\\/\/||\||\|//||/\\//\/||/\\\|..."""

// The permutation key length must be a factor of the ciphertext length.
// We restrict to all keys that are shorter than 8, since it will take too long 
// to brute-force longer permutations.
val possiblePermLengths = factorise(ciphertext.length).filter(_ < 8 && _ > 1)(*\clabel{perm_length_finder}*)

// Generate all possible permutations of the key length.
val permutations =
    possiblePermLengths.flatMap(length => (0 until length).permutations)(*\clabel{perm_gen}*)

// For each permutation, calculate the number of distinct blocks and the 
// transposed ciphertext.
val permutationResults = permutations
    .map(permutationKey => { // For each possible permutation key...
        val transposedCiphertext = ciphertext
            // Group into blocks of the key size
            .grouped(permutationKey.size)
            // Rearrange the blocks according to the permutation key
            .flatMap(block => permutationKey.map(index => block(index)))
            .toSeq(*\clabel{decipher_perm}*)

        // Count the number of distinct blocks of length 6 in the transposed ciphertext.
        val numberOfDistinctBlocks =
            transposedCiphertext.grouped(6).distinct.size(*\clabel{distinct_blocks}*)
        // Return the permutation key, the number of distinct blocks, and the 
        // transposed ciphertext.
        (permutationKey, numberOfDistinctBlocks, transposedCiphertext)
    })
    // Sort by the number of distinct blocks ascending. The 2nd value of the 
    // tuple is the number of distinct blocks.
    .sortBy(tuple => tuple._2)

// Print the first 10 keys and the number of distinct blocks for debugging.
println(permutationResults.take(10).map(x => (x._1, x._2)).mkString("\n"))(*\clabel{output_result}*)
// Take the transposed ciphertext of the permutation that resulted in the fewest
//  distinct blocks. Since the list is sorted, it's the first item
val transposedCiphertext = permutationResults.head._3

// Convert the transposed ciphertext that uses Wheatstone's encoding to an 
// English alphabet for substitution decryption.
// This is done by first finding all possible distinct blocks of 6 characters...
val alphabet = transposedCiphertext.grouped(6).toSeq.distinct
// ...and then using these to map the transposed ciphertext to english letters
val textInEnglishAlphabet = transposedCiphertext
    .grouped(6)
    .map(letter => (alphabet.indexOf(letter) + 'A').toChar)
    .mkString(*\clabel{conv_subst}*)

// Decrypt the text using a custom hill-climbing algorithm.
val brokenData = MonoAlphabeticSubstitutionBreaker.break(textInEnglishAlphabet)(*\clabel{subst_breaker}*)
// Output the result
println(brokenData.textData)
