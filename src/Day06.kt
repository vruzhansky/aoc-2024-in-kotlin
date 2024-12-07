import Direction.U
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration.Companion.nanoseconds

private const val DAY = "06"

private enum class Direction(val rowAdjust: Int, val colAdjust: Int) {
    U(-1, 0),
    R(0, 1),
    D(1, 0),
    L(0, -1);

    fun turnRight() = entries[(ordinal + 1) % 4]
}

private data class Point(val r: Int, val c: Int) {
    fun move(direction: Direction): Point = copy(r = r + direction.rowAdjust, c = c + direction.colAdjust)
}

private fun List<List<Char>>.inGrid(point: Point) = point.r in indices && point.c in this[0].indices
private fun List<List<Char>>.getAt(point: Point) = this[point.r][point.c]

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
        val current = System.nanoTime()
        runBlocking {
            pointsInWay.forEach { point ->
                launch {
                    val mutableGrid = grid.map { it.toMutableList() }
                    mutableGrid[point.r][point.c] = '#'
                    if (isLoop(start, mutableGrid)) loops.incrementAndGet()
                }

            }
        }
        println("Done in ${(System.nanoTime() - current).nanoseconds.inWholeMilliseconds} ms")
        return loops.get()
    }

    val testInput = readInput("Day${DAY}_test")
    check(part1(testInput).also { println(it) } == 41)
    check(part2(testInput).also { println(it) } == 6)

    val input = readInput("Day${DAY}")
    check(part1(input).also { println(it) } == 4656)
    check(part2(input).also { println(it) } == 1575)
}
