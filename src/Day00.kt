private const val DAY = "00"

fun main() {

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day${DAY}_test")
    checkAnMeasureTime(0) { part1(testInput) }
    checkAnMeasureTime(0) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(0) { part1(input) }
    checkAnMeasureTime(0) { part2(input) }
}
