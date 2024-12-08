import Direction.U
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger

private const val DAY = "06"

fun main() {

    fun parse(input: List<String>): Pair<List<List<Char>>, Pair<Point, Direction>> {
        var start: Pair<Point, Direction>? = null
        val grid = input.mapIndexed { rowIdx, row ->
            row.mapIndexed { colIndex, col ->
                if (col == '^') start = Point(rowIdx, colIndex) to U
                col
            }
        }.toList()
        return grid to start!!
    }

    fun move(start: Pair<Point, Direction>, grid: List<List<Char>>): Pair<Point, Direction>? {
        val (point, dir) = start
        val next = point.move(dir)
        return when {
            !grid.inGrid(next) -> null
            grid.getAt(next) != '#' -> next to dir
            else -> move(point to dir.turnRight(), grid)
        }
    }

    fun pointsInWay(start: Pair<Point, Direction>, grid: List<List<Char>>): Set<Point> {
        var cur: Pair<Point, Direction>? = start
        val visited = mutableSetOf<Point>()
        while (cur != null) {
            val point = cur.first
            if (!visited.contains(point)) {
                visited.add(point)
            }
            cur = move(cur, grid)
        }
        return visited
    }

    fun part1(input: List<String>): Int {
        val (grid, start) = parse(input)
        return pointsInWay(start, grid).size
    }

    fun isLoop(start: Pair<Point, Direction>, grid: List<List<Char>>): Boolean {
        var current: Pair<Point, Direction>? = start
        val visited = mutableSetOf<Pair<Point, Direction>>()
        while (current != null) {
            if (visited.contains(current)) return true
            visited.add(current)
            current = move(current, grid)
        }
        return false
    }

    fun part2(input: List<String>): Int {
        val (grid, start) = parse(input)
        val pointsInWay = pointsInWay(start, grid)
        val loops = AtomicInteger()
        runBlocking {
            pointsInWay.forEach { point ->
                launch {
                    val mutableGrid = grid.map { it.toMutableList() }
                    mutableGrid[point.r][point.c] = '#'
                    if (isLoop(start, mutableGrid)) loops.incrementAndGet()
                }

            }
        }
        return loops.get()
    }

    val testInput = readInput("Day${DAY}_test")
    checkAnMeasureTime(41) { part1(testInput) }
    checkAnMeasureTime(6) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(4656) { part1(input) }
    checkAnMeasureTime(1575) { part2(input) }
}
