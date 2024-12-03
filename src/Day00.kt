private const val DAY = "00"

fun main() {

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day${DAY}_test")
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)

    val input = readInput("Day${DAY}")
    part1(input).println()
    part2(input).println()
}
