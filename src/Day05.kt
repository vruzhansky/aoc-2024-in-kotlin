private const val DAY = "05"

fun main() {

    fun parse(input: List<String>): Pair<HashMap<Int, MutableSet<Int>>, ArrayList<List<Int>>> {
        val orderMap = HashMap<Int, MutableSet<Int>>()
        val updates = ArrayList<List<Int>>()

        input.forEach { line ->
            if (line.contains("|")) {
                val (first, second) = line.split("|").map { it.toInt() }
                orderMap.getOrPut(first) { mutableSetOf() }.add(second)
            } else if (line != "") {
                updates.add(line.split(",").map { it.toInt() })
            }
        }
        return Pair(orderMap, updates)
    }

    fun sortUpdate(update: List<Int>, orderMap: Map<Int, Set<Int>>): List<Int> =
        update.sortedWith { a, b ->
            if (orderMap[a]?.contains(b) == true) -1 else 1
        }

    fun part1(input: List<String>): Int {
        val (orderMap, updates) = parse(input)

        return updates.fold(0) { acc, update ->
            val sorted = sortUpdate(update, orderMap)
            acc + if (sorted == update) sorted[sorted.size / 2] else 0
        }
    }


    fun part2(input: List<String>): Int {
        val (orderMap, updates) = parse(input)

        return updates.fold(0) { acc, update ->
            val sorted = sortUpdate(update, orderMap)
            acc + if (sorted != update) sorted[sorted.size / 2] else 0
        }
    }

    val testInput = readInput("Day${DAY}_test")
    check(part1(testInput).also { println(it) } == 143)
    check(part2(testInput).also { println(it) } == 123)

    val input = readInput("Day${DAY}")
    check(part1(input).also { println(it) } == 4637)
    check(part2(input).also { println(it) } == 6370)
}
