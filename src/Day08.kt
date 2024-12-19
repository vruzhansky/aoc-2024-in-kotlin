import utils.Point
import utils.inGrid
import utils.toCharGrid

private const val DAY = "08"

fun main() {

    fun parse(input: List<String>): Pair<List<List<Char>>, Map<Char, List<Point>>> {
        val grid = input.toCharGrid()
        val charMap = mutableMapOf<Char, MutableList<Point>>()

        grid.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, char ->
                charMap.getOrPut(char) { mutableListOf() }.add(Point(rowIdx, colIdx))
            }
        }
        return grid to charMap
    }

    fun part1(input: List<String>): Int {
        val (grid, charMap) = parse(input)

        val locos = mutableSetOf<Point>()

        for ((char, points) in charMap.entries) {
            if (char == '.') continue
            for (i in points.indices) {
                for (j in i + 1 until points.size) {
                    val diff = points[j] - points[i]

                    val cand1 = points[i] - diff
                    val cand2 = points[j] + diff

                    if (grid.inGrid(cand1)) {
                        locos.add(cand1)
                    }
                    if (grid.inGrid(cand2)) {
                        locos.add(cand2)
                    }
                }
            }
        }

        return locos.size
    }

    fun part2(input: List<String>): Int {
        val (grid, charMap) = parse(input)

        val locos = mutableSetOf<Point>()

        for ((char, points) in charMap.entries) {
            if (char == '.') continue
            for (i in points.indices) {
                for (j in i + 1 until points.size) {
                    val diff = points[j] - points[i]

                    var cand1 = points[i]
                    while (grid.inGrid(cand1)) {
                        locos.add(cand1)
                        cand1 -= diff
                    }

                    var cand2 = points[j]
                    while (grid.inGrid(cand2)) {
                        locos.add(cand2)
                        cand2 += diff
                    }
                }
            }
        }
        return locos.size
    }

    val testInput = readInput("Day${DAY}_test")
    checkAnMeasureTime(14) { part1(testInput) }
    checkAnMeasureTime(34) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(348) { part1(input) }
    checkAnMeasureTime(1221) { part2(input) }
}
