import Direction2.*

private const val DAY = "12"

fun main() {

    fun flood(start: Point, area: MutableSet<Point>, visited: MutableSet<Point>, grid: List<List<Char>>) {
        val id = grid.getAt(start)
        area.add(start)
        visited.add(start)

        start.neighbours()
            .filter { grid.inGrid(it) && grid.getAt(it) == id && it !in visited }
            .forEach { flood(it, area, visited, grid) }
    }

    fun doFlood(input: List<String>): List<Pair<Char, Set<Point>>> {
        val grid = input.toCharGrid()
        val areas = mutableListOf<Pair<Char, MutableSet<Point>>>()
        val visited = mutableSetOf<Point>()

        grid.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, char ->
                val start = Point(rowIdx, colIdx)
                if (start !in visited) {
                    val area = mutableSetOf<Point>()
                    areas.add(char to area)
                    flood(start, area, visited, grid)
                }
            }
        }
        return areas
    }

    fun part1(input: List<String>): Int {
        val areas = doFlood(input)

        var total = 0
        areas.forEach { (_, area) ->
            val perimeter = area.fold(0) { acc, point -> acc + point.neighbours().count { !area.contains(it) } }
            total += perimeter * area.size
        }
        return total

    }

    data class Angle(val r1: Int, val r2: Int, val c1: Int, val c2: Int)

    fun part2(input: List<String>): Int {
        val areas = doFlood(input)

        var total = 0
        areas.forEach { (_, area) ->
            val angles = mutableSetOf<Angle>()
            area.forEach { point ->
                val points = point.directedNeighbours2()
                // outer
                if (setOf(L, U).all { !area.contains(points[it]) }) {
                    angles.add(Angle(point.r, point.r - 1, point.c, point.c - 1))
                }
                if (setOf(R, U).all { !area.contains(points[it]) }) {
                    angles.add(Angle(point.r, point.r - 1, point.c, point.c + 1))
                }
                if (setOf(L, D).all { !area.contains(points[it]) }) {
                    angles.add(Angle(point.r, point.r + 1, point.c, point.c - 1))
                }
                if (setOf(R, D).all { !area.contains(points[it]) }) {
                    angles.add(Angle(point.r, point.r + 1, point.c, point.c + 1))
                }
                // inner
                if (setOf(L, U).all { area.contains(points[it]) } && !area.contains(points[UL])) {
                    angles.add(Angle(point.r, point.r - 1, point.c, point.c - 1))
                }
                if (setOf(R, U).all { area.contains(points[it]) } && !area.contains(points[UR])) {
                    angles.add(Angle(point.r, point.r - 1, point.c, point.c + 1))
                }
                if (setOf(L, D).all { area.contains(points[it]) } && !area.contains(points[DL])) {
                    angles.add(Angle(point.r, point.r + 1, point.c, point.c - 1))
                }
                if (setOf(R, D).all { area.contains(points[it]) } && !area.contains(points[DR])) {
                    angles.add(Angle(point.r, point.r + 1, point.c, point.c + 1))
                }
            }
            total += angles.size * area.size
        }
        return total
    }

    val testInput = readInput("Day${DAY}_test")
    checkAnMeasureTime(1930) { part1(testInput) }
    checkAnMeasureTime(1206) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(1464678) { part1(input) }
    checkAnMeasureTime(877492) { part2(input) }
}
