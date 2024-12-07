import kotlin.time.measureTime

private const val DAY = "07"

fun main() {

    fun parse(input: List<String>): List<Pair<Long, List<Long>>> {
        val resAndNums = mutableListOf<Pair<Long, List<Long>>>()
        input.forEach { row ->
            val (res, nums) = row.split(": ")
            val intNums = nums.split(" ").map { it.toLong() }
            resAndNums.add(res.toLong() to intNums)
        }
        return resAndNums
    }

    fun part1(input: List<String>): Long {
        val resAndNums = parse(input)

        var total = 0L
        for ((res, nums) in resAndNums) {
            var results = setOf(nums.first())
            for (num in nums.drop(1)) {
                val newResults = HashSet<Long>()
                for (result in results) {
                    newResults.add(result + num)
                    newResults.add(result * num)
                }
                results = newResults
            }
            if (results.contains(res)) total += res
        }

        return total
    }

    fun part2(input: List<String>): Long {
        val resAndNums = parse(input)

        var total = 0L
        for ((res, nums) in resAndNums) {
            var results = setOf(nums.first())
            for (num in nums.drop(1)) {
                val newResults = HashSet<Long>()
                for (result in results) {
                    (result + num).takeIf { it <= res }?.let { newResults.add(it) }
                    (result * num).takeIf { it <= res }?.let { newResults.add(it) }
                    ("$result$num".toLong()).takeIf { it <= res }?.let { newResults.add(it) }
                }
                results = newResults
            }
            if (results.contains(res)) total += res
        }
        return total
    }

    val testInput = readInput("Day${DAY}_test")
    check(part1(testInput).also { println(it) } == 3749L)
    check(part2(testInput).also { println(it) } == 11387L)

    val input = readInput("Day${DAY}")
    measureTime {
        check(part1(input).also { println(it) } == 538191549061L)
    }.also { println("Done in ${it.inWholeMilliseconds} ms") }
    measureTime {
        check(part2(input).also { println(it) } == 34612812972206L)
    }.also { println("Done in ${it.inWholeMilliseconds} ms") }
}
