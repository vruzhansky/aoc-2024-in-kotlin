import kotlin.time.measureTime

private const val DAY = "00"

fun main() {

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day${DAY}_test")
    check(part1(testInput).also { println(it) } == 0)
    check(part2(testInput).also { println(it) } == 0)

    val input = readInput("Day${DAY}")
    measureTime {
        check(part1(input).also { println(it) } == 0)
    }.also { println("Done in ${it.inWholeMilliseconds} ms") }
    measureTime {
        check(part2(input).also { println(it) } == 0)
    }.also { println("Done in ${it.inWholeMilliseconds} ms") }
}
