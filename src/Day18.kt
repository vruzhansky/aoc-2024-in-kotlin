import utils.*
import kotlin.math.abs

private const val DAY = "18"

fun main() {

    fun neighboursWithCost(grid: List<List<Char>>): Point.() -> Sequence<Pair<Point, Int>> =
        {
            neighbours().asSequence()
                .filter { p -> grid.inGrid(p) }
                .filter { p -> grid.getAt(p) != '#' }
                .map { p -> p to 1 }
        }

    fun findPath(start: Point, end: Point, grid: List<List<Char>>): Node<Point> {
        val node = aStar(
            from = start,
            goal = { it == end },
            neighboursWithCost = neighboursWithCost(grid),
            heuristic = { abs(it.r - end.r) + abs(it.c - end.c) }
        )

        // print
        val mGrid = grid.map { it.toMutableList() }
        var current: Node<Point>? = node
        while (current != null) {
            val value = current.value
            mGrid[value.r][value.c] = 'O'
            current = current.parent
        }

        println(mGrid.joinToString("\n") { it.joinToString("") })
        return node
    }

    fun part1(gridSize: Int = 71, maxBytes: Int = 1024, input: List<String>): Int {
        val grid = MutableList(gridSize) { MutableList(gridSize) { '.' } }

        input.forEachIndexed { idx, line ->
            if (idx < maxBytes) {
                val (c, r) = line.split(",").map { it.toInt() }
                grid[r][c] = '#'
            }
        }

        val node = findPath(Point(0, 0), Point(gridSize - 1, gridSize - 1), grid)
        return node.cost
    }

    fun part2(gridSize: Int = 71, maxBytes: Int = 1024, input: List<String>): String {
        val grid = MutableList(gridSize) { MutableList(gridSize) { '.' } }
        val restBytes = mutableListOf<Point>()
        input.forEachIndexed { idx, line ->
            val (c, r) = line.split(",").map { it.toInt() }
            if (idx < maxBytes) {
                grid[r][c] = '#'
            } else {
                restBytes.add(Point(r, c))
            }
        }

        var res: Point? = null
        val mGrid = grid.map { it.toMutableList() }
        while (restBytes.isNotEmpty()) {
            val blocked = restBytes.removeFirst()
            mGrid[blocked.r][blocked.c] = '#'
            try {
                findPath(Point(0, 0), Point(gridSize - 1, gridSize - 1), mGrid)
            } catch (e: Exception) {
                println("Can't find path, blocked by $blocked")
                res = blocked
                break
            }
        }

        if (res == null) {
            error("Can't block")
        }
        return "${res.c},${res.r}"
    }

    val testInput = readInput("Day${DAY}_test")
    checkAnMeasureTime(22) { part1(7, 12, testInput) }
    checkAnMeasureTime("6,1") { part2(7, 12, testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(304) { part1(input = input) }
    checkAnMeasureTime("50,28") { part2(input = input) }
}
