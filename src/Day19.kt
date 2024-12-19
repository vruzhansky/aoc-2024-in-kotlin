private const val DAY = "19"

fun main() {

    fun test1(stripes: List<String>, towel: String, cache: MutableMap<String, Boolean>): Boolean {
        if (towel.isEmpty()) {
            return true
        }
        if (cache.containsKey(towel)) {
            return cache[towel]!!
        }
        var found = false
        for (stripe in stripes) {
            if (towel.startsWith(stripe)) {
                found = test1(stripes, towel.removePrefix(stripe), cache)
                if (found) break
            }
        }
        cache[towel] = found
        return found
    }

    fun test2(stripes: List<String>, towel: String, cache: MutableMap<String, Long>): Long {
        if (towel.isEmpty()) {
            return 1L
        }
        if (cache.containsKey(towel)) {
            return cache[towel]!!
        }
        var count = 0L
        for (stripe in stripes) {
            if (towel.startsWith(stripe)) {
                count += test2(stripes, towel.removePrefix(stripe), cache)
            }
        }
        cache[towel] = count
        return count
    }

    fun part1(input: List<String>): Int {
        val stripes = input.first().split(", ")
        val towels = input.drop(2)

        val cache = mutableMapOf<String, Boolean>()
        var count = 0
        towels.forEach {
            println("Testing: $it")
            val res = test1(stripes, it, cache)
            if (res) {
                println("Matched!")
                count++
            } else {
                println("Failed!")
            }
        }

        return count
    }

    fun part2(input: List<String>): Long {
        val stripes = input.first().split(", ")
        val towels = input.drop(2)

        val cache = mutableMapOf<String, Long>()
        var count = 0L
        towels.forEach {
            println("Testing: $it")
            val res = test2(stripes, it, cache)
            if (res > 0) {
                println("Matched! $res")
            } else {
                println("Failed!")
            }
            count += res
        }

        return count
    }

    val testInput = readInput("Day${DAY}_test")
    checkAnMeasureTime(6) { part1(testInput) }
    checkAnMeasureTime(16) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(298) { part1(input) }
    checkAnMeasureTime(572248688842069) { part2(input) }
}
