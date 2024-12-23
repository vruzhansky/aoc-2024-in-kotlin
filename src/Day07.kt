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

    fun Long.addToResults(target: Long, results: MutableSet<Long>) = takeIf { it <= target }?.let { results.add(it) }

    fun part1(input: List<String>): Long {
        val resAndNums = parse(input)

        var total = 0L
        for ((res, nums) in resAndNums) {
            var results = setOf(nums.first())
            for (num in nums.drop(1)) {
                val newResults = HashSet<Long>()
                for (result in results) {
                    (result + num).addToResults(res, newResults)
                    (result * num).addToResults(res, newResults)
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
                    (result + num).addToResults(res, newResults)
                    (result * num).addToResults(res, newResults)
                    "$result$num".toLong().addToResults(res, newResults)
                }
                results = newResults
            }
            if (results.contains(res)) total += res
        }
        return total
    }

    val testInput = readInput("Day${DAY}_test")
    checkAnMeasureTime(3749L) { part1(testInput) }
    checkAnMeasureTime(11387L) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(538191549061L) { part1(input) }
    checkAnMeasureTime(34612812972206L) { part2(input) }
}
