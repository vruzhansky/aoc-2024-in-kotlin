data class Point(val r: Int, val c: Int) {
    operator fun minus(point: Point) = Point(this.r - point.r, this.c - point.c)
    operator fun plus(point: Point) = Point(this.r + point.r, this.c + point.c)

    fun move(direction: Direction): Point = copy(r = r + direction.rowAdjust, c = c + direction.colAdjust)
    fun neighbours():List<Point> = listOf(move(Direction.U), move(Direction.R), move(Direction.D), move(Direction.L))
}

enum class Direction(val rowAdjust: Int, val colAdjust: Int) {
    U(-1, 0),
    R(0, 1),
    D(1, 0),
    L(0, -1);

    fun turnRight() = entries[(ordinal + 1) % 4]
    fun turnLeft() = entries[(ordinal + 3) % 4]
}

fun List<String>.toCharGrid() = map { it.toCharArray().toList() }
fun List<String>.toIntGrid() = map { it.toCharArray().map {char -> char.digitToInt()} .toList() }
fun <T> List<List<T>>.inGrid(point: Point) = point.r in indices && point.c in this[0].indices
fun <T> List<List<T>>.getAt(point: Point) = this[point.r][point.c]

