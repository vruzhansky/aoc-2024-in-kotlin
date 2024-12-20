package utils

data class Point(val r: Int, val c: Int) {
    operator fun minus(point: Point) = Point(this.r - point.r, this.c - point.c)
    operator fun plus(point: Point) = Point(this.r + point.r, this.c + point.c)

    fun move(direction: Direction): Point = copy(r = r + direction.rowAdjust, c = c + direction.colAdjust)

    fun move(direction: Direction2): Point = copy(r = r + direction.rowAdjust, c = c + direction.colAdjust)

    fun move(adjust: Pair<Int, Int>): Point = copy(r = r + adjust.first, c = c + adjust.second)

    fun <T> moveFlip(adjust: Pair<Int, Int>, grid: List<List<T>>): Point {
        val new = copy(r = r + adjust.first, c = c + adjust.second)
        return if (grid.inGrid(new)) {
            new
        } else {
            val rows = grid.size
            val cols = grid[0].size
            new.copy(r = flip(new.r, rows), c = flip(new.c, cols))
        }
    }

    private fun flip(coordinate: Int, max: Int): Int =
        when {
            coordinate >= max -> coordinate - max
            coordinate < 0 -> coordinate + max
            else -> coordinate
        }

    fun circle(radius: Int): List<Point> {
        val res = mutableListOf<Point>()
        for (offset in 0..<radius) {
            val invOffset = radius - offset
            res.addAll(
                listOf(
                    Point(r + offset, c + invOffset),
                    Point(r + invOffset, c - offset),
                    Point(r - offset, c - invOffset),
                    Point(r - invOffset, c + offset),
                )
            )
        }
        return res
    }

    fun neighbours(): List<Point> = Direction.entries.map { move(it) }
    fun directedNeighbours() = Direction.entries.associateWith { move(it) }
    fun directedNeighbours2() = Direction2.entries.associateWith { move(it) }
}

enum class Direction(val rowAdjust: Int, val colAdjust: Int, val char: Char) {
    U(-1, 0, '^'),
    R(0, 1, '>'),
    D(1, 0, 'v'),
    L(0, -1, '<');

    fun turnRight() = entries[(ordinal + 1) % 4]
    fun turnLeft() = entries[(ordinal + 3) % 4]

    fun reverse() = entries[(ordinal + 2) % 4]
}

enum class Direction2(val rowAdjust: Int, val colAdjust: Int) {
    U(-1, 0),
    UL(-1, -1),
    UR(-1, 1),
    R(0, 1),
    D(1, 0),
    DR(1, 1),
    DL(1, -1),
    L(0, -1);
}

fun List<String>.toCharGrid() = map { it.toCharArray().toList() }
fun List<String>.toIntGrid() = map { it.toCharArray().map { char -> char.digitToInt() }.toList() }
fun <T> List<List<T>>.inGrid(point: Point) = point.r in indices && point.c in this[0].indices
fun <T> List<List<T>>.getAt(point: Point) = this[point.r][point.c]

