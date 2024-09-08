package com.core.evolutionaryalgorithms

object ChildSelectionPolicy {
    private val rng = new scala.util.Random(3)
    def random(currentScore: Double, newScore: Double): Boolean = {
        rng.nextBoolean()
    }
    def bestScore(currentScore: Double, newScore: Double): Boolean = {
        newScore > currentScore
    }
    def newer(currentScore: Double, newScore: Double): Boolean = {
        true
    }
    def expDfOverT(temperature: Double)(currentScore: Double, newScore: Double): Boolean = {
        val delta = newScore - currentScore
        val probability = Math.exp(delta / temperature)
        rng.nextDouble() < probability
    }
}
