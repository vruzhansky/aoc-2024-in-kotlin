import kotlin.math.abs

private const val DAY = "01"

fun main() {
    fun parseInput(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()
        input.forEach {
            val (first, second) = it.split("   ")
            firstList.add(first.toInt())
            secondList.add(second.toInt())
        }
        return Pair(firstList, secondList)
    }

    fun part1(input: List<String>): Int {
        val (firstList, secondList) = parseInput(input)

        firstList.sort()
        secondList.sort()
        var total = 0
        firstList.zip(secondList) { first, second -> total += abs(first - second) }
        return total
    }

    fun part2(input: List<String>): Int {
        val (firstList, secondList) = parseInput(input)

        val freqMap = HashMap<Int, Int>()
        secondList.associateWithTo(freqMap) { freqMap.getOrDefault(it, 0) + 1 }

        return firstList.fold(0) { acc, i -> acc + i * freqMap.getOrDefault(i, 0) }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day${DAY}_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day${DAY}")
    part1(input).println()
    part2(input).println()
}
