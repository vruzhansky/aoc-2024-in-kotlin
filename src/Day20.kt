import utils.*
import kotlin.math.abs

private const val DAY = "20"

fun main() {

    fun findPath(start: Point, end: Point, grid: List<List<Char>>, print: Boolean = false): Node<Point> {
        val node = aStar(
            from = start,
            goal = { it == end },
            neighboursWithCost = {
                neighbours().asSequence()
                    .filter { p -> grid.inGrid(p) }
                    .filter { p -> grid.getAt(p) != '#' }
                    .map { p -> p to 1 }
            },
            heuristic = { abs(it.r - end.r) + abs(it.c - end.c) }
        )

        // print
        if (print) {
            val mgrid = grid.map { it.toMutableList() }
            node.populateGrid(mgrid) { 'O' }
            println(mgrid.joinToString("\n") { it.joinToString("") })
        }
        return node
    }

    fun parse(input: List<String>): Pair<List<List<Char>>, Pair<Point, Point>> {
        val grid = input.map { it.toMutableList() }
        val startAndEnd = mutableListOf<Point>()

        grid.forEachIndexed { rowIdx, line ->
            line.forEachIndexed { colIdx, _ ->
                if (grid[rowIdx][colIdx] == 'S') {
                    startAndEnd.addFirst(Point(rowIdx, colIdx))
                }
                if (grid[rowIdx][colIdx] == 'E') {
                    startAndEnd.addLast(Point(rowIdx, colIdx))
                }
            }
        }
        return grid to (startAndEnd.first() to startAndEnd.last())
    }

    fun solve(
        grid: List<List<Char>>,
        startAndEnd: Pair<Point, Point>,
        radius: IntRange = 2..2,
        target: Int = 100
    ): MutableList<Pair<Point, Point>> {
        val (start, end) = startAndEnd

        val node = findPath(start, end, grid)

        val path = node.path()
        val pathIdx = path.withIndex().associate { it.value to it.index }

        val cheats = mutableListOf<Pair<Point, Point>>()
        path.dropLast(1).forEach { point ->
            radius.asSequence()
                .flatMap { r -> point.circle(r).map { it to r } }
                .filter { (p, r) -> pathIdx.contains(p) && pathIdx[p]!! - pathIdx[point]!! - r >= target }
                .forEach { (p, _) -> cheats.add(point to p) }
        }
        return cheats
    }

    fun part1(input: List<String>): Int {
        val (grid, startAndEnd) = parse(input)
        val cheats = solve(grid, startAndEnd)

        return cheats.size
    }

    fun part2(input: List<String>): Int {
        val (grid, startAndEnd) = parse(input)
        val cheats = solve(grid, startAndEnd, 2..20)

        return cheats.size
    }

    val testInput = readInput("Day${DAY}_test")
//    checkAnMeasureTime(44) { part1(testInput) }
//    checkAnMeasureTime(0) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(1351) { part1(input) }
    checkAnMeasureTime(966130) { part2(input) }
}
