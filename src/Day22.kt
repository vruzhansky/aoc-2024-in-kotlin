private const val DAY = "22"

fun main() {

    fun secretNumber(previous: Long): Long {
        val first = previous
            .shl(6)
            .xor(previous).and(16777215)
        val second = first
            .shr(5)
            .xor(first).and(16777215)
        return second
            .shl(11)
            .xor(second).and(16777215)
    }

    fun part1(input: List<String>): Long {
        val numbers = input.map { it.toLong() }

        var res = 0L
        for (number in numbers) {
            var current = number
            repeat(2000) {
                current = secretNumber(current)
            }
            res += current
        }
        return res
    }

    fun part2(input: List<String>): Int {
        val numbers = input.map { it.toLong() }

        val priceMap = mutableMapOf<List<Int>, Int>()

        for (number in numbers) {
            val prices = mutableListOf((number % 10).toInt())
            var current = number
            repeat(2000) {
                current = secretNumber(current)
                prices.add((current % 10).toInt())
            }
            val priceChanges = prices.windowed(2).map { (a, b) -> b - a }
            val seen = mutableSetOf<List<Int>>()
            priceChanges.withIndex().windowed(4).forEach { window ->
                val seq = window.subList(0, 4).map { it.value }
                val price = prices[window.last().index + 1]
                if (!seen.contains(seq)) {
                    priceMap.merge(seq, price) { a, b -> a + b }
                    seen.add(seq)
                }
            }
        }

        val max = priceMap.maxBy { it.value }
        println("Max: $max")
        return max.value
    }

    val testInput = readInput("Day${DAY}_test")
//    checkAnMeasureTime(37327623) { part1(testInput) }
    checkAnMeasureTime(23) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(17965282217) { part1(input) }
    checkAnMeasureTime(2152) { part2(input) }
}
