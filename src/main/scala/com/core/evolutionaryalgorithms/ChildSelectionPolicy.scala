package com.core.evolutionaryalgorithms

object ChildSelectionPolicy {
    private val rng = new scala.util.Random(3)
    def random(currentScore: Double, newScore: Double, generation: Int, maxGenerations: Int): Boolean = {
        rng.nextBoolean()
    }
    def bestScore(currentScore: Double, newScore: Double, generation: Int, maxGenerations: Int): Boolean = {
        newScore > currentScore
    }
    def newer(currentScore: Double, newScore: Double, generation: Int, maxGenerations: Int): Boolean = {
        true
    }
    def expDfOverT(startTemperature: Double, endTemperature: Double)(currentScore: Double, newScore: Double, generation: Int, maxGenerations: Int): Boolean = {
        val temperature = startTemperature + (endTemperature - startTemperature) * generation / maxGenerations
        val delta = newScore - currentScore
        val probability = Math.exp(delta / temperature)
        rng.nextDouble() < probability
    }
}
