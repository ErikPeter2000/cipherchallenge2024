package com.core.evolutionaryalgorithms

/** Determines the policy for selecting a child in an evolutionary algorithm.
  */
object ChildSelectionPolicy {
    private val rng = new scala.util.Random(3)

    /** Select the new child with a probability of 0.5.
      */
    def uniformRandom(currentScore: Double, newScore: Double, generation: Int, maxGenerations: Int): Boolean = {
        rng.nextBoolean()
    }

    /** Select the new child if it has a better score than the current child.
      */
    def bestScore(currentScore: Double, newScore: Double, generation: Int, maxGenerations: Int): Boolean = {
        newScore > currentScore
    }

    /** Always select the new child.
      */
    def alwaysNewer(currentScore: Double, newScore: Double, generation: Int, maxGenerations: Int): Boolean = {
        true
    }

    /** Select the new child noisily based on the "temperature" of the system.
      *
      * If the new child has a better score, it is always selected. However, a worse child has a possibility of also
      * being selected. The greater the difference between the current score and the child score, the less likely it is
      * to be selected. Increasing the temperature will increase the probability of selecting a worse child.
      *
      * This is useful for simulated annealing.
      */
    def expDfOverT(
        startTemperature: Double,
        endTemperature: Double
    )(currentScore: Double, newScore: Double, generation: Int, maxGenerations: Int): Boolean = {
        val temperature = startTemperature + (endTemperature - startTemperature) * generation / maxGenerations
        val delta = newScore - currentScore
        val probability = Math.exp(delta / temperature)
        rng.nextDouble() < probability
    }
}
