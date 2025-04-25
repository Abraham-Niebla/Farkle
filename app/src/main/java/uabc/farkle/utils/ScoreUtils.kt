package uabc.farkle.utils

fun calculateScore(dice: List<Int>): Int {
    if (dice.isEmpty()) {
        return 0
    }

    val counts = dice.groupingBy { it }.eachCount()
    var score = 0

    for (die in 1..6) {
        val count = counts[die] ?: 0
        val numTrios = count / 3
        if (numTrios > 0) {
            score += numTrios * when (die) {
                1 -> 1000
                else -> die * 100
            }
        }
    }

    // Puntuación de unos y cincos individuales (después de contar los tríos)
    val remainingOnes = (counts[1] ?: 0) % 3
    score += remainingOnes * 100

    val remainingFives = (counts[5] ?: 0) % 3
    score += remainingFives * 50

    return score
}