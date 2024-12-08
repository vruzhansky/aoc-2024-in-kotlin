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

    val testInput = readInput("Day${DAY}_test")
    checkAnMeasureTime(11) { part1(testInput) }
    checkAnMeasureTime(31) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(2580760) { part1(input) }
    checkAnMeasureTime(25358365) { part2(input) }
}
