private const val DAY = "02"

fun main() {
    fun parseInput(input: List<String>): List<List<Int>> {
        val list = mutableListOf<List<Int>>()
        input.forEach {
            list.add(it.split(" ").map { el -> el.toInt() }.toList())
        }
        return list
    }

    fun isSafe(row: List<Int>): Boolean {
        val inc = row.first() < row.last()

        for (i in 1..row.lastIndex) {
            val prev = row[i - 1]
            val next = row[i]
            val diff = next - prev

            if (!(inc && diff in 1..3 || !inc && diff in -3..-1)) {
                return false
            }
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val list = parseInput(input)
        return list.fold(0) { acc, row -> acc + if (isSafe(row)) 1 else 0 }
    }

    fun part2(input: List<String>): Int {
        val list = parseInput(input)

        var total = 0
        for (row in list) {
            if (isSafe(row)) {
                total++
            } else {
                for (i in 0..row.lastIndex) {
                    if (isSafe(row.toMutableList().also { it.removeAt(i) })) {
                        total++
                        break
                    }
                }
            }
        }
        return total
    }

    val testInput = readInput("Day${DAY}_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day${DAY}")
    part1(input).println()
    part2(input).println()
}
