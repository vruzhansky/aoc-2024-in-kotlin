import kotlin.time.measureTime

private const val DAY = "08"

fun main() {
   data class Point(val r: Int, val c: Int) {
       operator fun minus(point: Point) = Point(this.r - point.r, this.c - point.c)
       operator fun plus(point: Point) = Point(this.r + point.r, this.c + point.c)
   }

   fun List<List<Char>>.inGrid(point: Point) = point.r in indices && point.c in this[0].indices

    fun parse(input: List<String>): Pair<List<List<Char>>, Map<Char, List<Point>>> {
        val grid = input.map { it.toCharArray().toList() }
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

                    val cand1= points[i] - diff
                    val cand2= points[j] + diff

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
    check(part1(testInput).also { println(it) } == 14)
    check(part2(testInput).also { println(it) } == 34)

    val input = readInput("Day${DAY}")
    measureTime {
        check(part1(input).also { println(it) } == 348)
    }.also { println("Done in ${it.inWholeMilliseconds} ms") }
    measureTime {
        check(part2(input).also { println(it) } == 1221)
    }.also { println("Done in ${it.inWholeMilliseconds} ms") }
}
