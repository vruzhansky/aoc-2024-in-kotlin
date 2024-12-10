private const val DAY = "10"

fun main() {

    fun parse(input: List<String>): Pair<List<List<Int>>, MutableList<Point>> {
        val grid = input.toIntGrid()
        val startPoints = mutableListOf<Point>()
        grid.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, value ->
                if (value == 0) startPoints.add(Point(rowIdx, colIdx))
            }
        }
        return Pair(grid, startPoints)
    }

    fun explore(trail: List<Point>, grid: List<List<Int>>, trails: MutableList<List<Point>>) {
        val from = trail.last()
        val height = grid[from.r][from.c]
        if (height == 9) {
            trails.add(trail)
            return
        }
        val neighbours = from.neighbours()
        neighbours.forEach { neighbour ->
            if (grid.inGrid(neighbour) && grid[neighbour.r][neighbour.c] == height + 1) {
                explore(trail + neighbour, grid, trails)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val (grid, startPoints) = parse(input)

        val trails = mutableListOf<List<Point>>()
        startPoints.forEach {
            explore(listOf(it), grid, trails)
        }

        return trails.map { it.first() to it.last() }.toSet().count()
    }

    fun part2(input: List<String>): Int {
        val (grid, startPoints) = parse(input)

        val trails = mutableListOf<List<Point>>()
        startPoints.forEach {
            explore(listOf(it), grid, trails)
        }
        return trails.count()
    }

    val testInput = readInput("Day${DAY}_test")
    checkAnMeasureTime(36) { part1(testInput) }
    checkAnMeasureTime(81) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(644) { part1(input) }
    checkAnMeasureTime(1366) { part2(input) }
}
