import utils.Point

private const val DAY = "14"

fun main() {

    fun score(grid: List<List<Int>>): Int {
        val qrows = grid.size / 2
        val qcols = grid[0].size / 2

        var first = 0
        var second = 0
        var third = 0
        var forth = 0

        grid.forEachIndexed { r, row ->
            row.forEachIndexed { c, value ->
                if (r < qrows && c < qcols) first += value
                if (r < qrows && c > qcols) second += value
                if (r > qrows && c < qcols) third += value
                if (r > qrows && c > qcols) forth += value
            }
        }

        return first * second * third * forth
    }

    fun parse(input: List<String>): List<Pair<Point, Pair<Int, Int>>> {
        val pointsAndSpeed = mutableListOf<Pair<Point, Pair<Int, Int>>>()
        input.forEach {
            val (pos, speed) = it.split(" ")
            val (x, y) = pos.removePrefix("p=").split(",").map { it.toInt() }
            val (dx, dy) = speed.removePrefix("v=").split(",").map { it.toInt() }
            pointsAndSpeed.add(Point(y, x) to (dy to dx))
        }
        return pointsAndSpeed
    }

    fun moveRobots(
        posAndSpeed: List<Pair<Point, Pair<Int, Int>>>,
        grid: MutableList<MutableList<Int>>
    ): List<Pair<Point, Pair<Int, Int>>> {
        val next = mutableListOf<Pair<Point, Pair<Int, Int>>>()
        posAndSpeed.forEach { (pos, speed) ->
            val newPos = pos.moveFlip(speed, grid)
            if (grid[pos.r][pos.c] > 0) grid[pos.r][pos.c] = grid[pos.r][pos.c] - 1
            grid[newPos.r][newPos.c] = grid[newPos.r][newPos.c] + 1

            next.add(newPos to speed)
        }
        return next
    }

    fun part1(rows: Int = 103, cols: Int = 101, input: List<String>): Int {
        val pointsAndSpeed = parse(input)
        val grid = MutableList(rows) { MutableList(cols) { 0 } }

        var current = pointsAndSpeed
        repeat(100) {
            current = moveRobots(current, grid)
        }

        return score(grid)
    }

    fun part2(rows: Int = 103, cols: Int = 101, input: List<String>): Int {
        val pointsAndSpeed = parse(input)
        val totalRobots = input.size
        val grid = MutableList(rows) { MutableList(cols) { 0 } }

        var current = pointsAndSpeed
        var iteration = 0
        val scores = mutableMapOf<Int, Int>()
        repeat(rows * cols) {
            current = moveRobots(current, grid)

            val positions = mutableSetOf<Point>().apply { addAll(current.map{it.first }) }
            iteration++

            if (positions.size == totalRobots) {
                println("Unique positions found after $iteration iterations")
                println(grid.joinToString("\n") { it.joinToString("").replace('0', ' ') })
            }
            scores[iteration] = score(grid)
        }
        // Can't justify it - discovered experimentally
        // - Christmas Tree appears when all robots are in unique positions
        // - Christmas Tree appears when score is minimum
        val minBy = scores.minBy { it.value }
        println("Min score $minBy")

        return minBy.key
    }

    val testInput = readInput("Day${DAY}_test")
//    checkAnMeasureTime(12) { part1(testInput) }
//    checkAnMeasureTime(0) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(215476074) { part1(103, 101, input) }
    checkAnMeasureTime(6285) { part2(103, 101, input) }
}
