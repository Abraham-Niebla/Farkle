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

fun calculateHotDices(dice: List<Int>): Boolean {
    if (dice.isEmpty()) {
        return false
    }

    val counts = dice.groupingBy { it }.eachCount().toMutableMap()
    var hotDices = true
    var aux = -1

    for (die in 1..6) {

        val count = counts[die] ?: 0
        val numTrios = count / 3

        if (numTrios > 0) {
            aux = counts[die] ?: 0
            counts[die] = aux - (3 * numTrios)
        }

    }

    // Puntuación de unos y cincos individuales (después de contar los tríos)
    counts[1] = 0
    counts[5] = 0

    for (count in counts.entries.iterator()) {
        hotDices = hotDices && (count.value == 0)
    }

    return hotDices
}