import kotlin.math.floor

private const val DAY = "13"

fun main() {

    data class Eq(val a: Long, val b: Long, val r: Long)

    fun part1(input: List<String>): Long {
        val equations = mutableListOf<Pair<Eq, Eq>>()
        input.windowed(4, 4, true) {
            val (ax, ay) = it[0].removePrefix("Button A: ").split(", ")
            val (bx, by) = it[1].removePrefix("Button B: ").split(", ")
            val (r1, r2) = it[2].removePrefix("Prize: ").split(", ")
            val first = Eq(ax.removePrefix("X+").toLong(), bx.removePrefix("X+").toLong(), r1.removePrefix("X=").toLong())
            val second = Eq(ay.removePrefix("Y+").toLong(), by.removePrefix("Y+").toLong(), r2.removePrefix("Y=").toLong())
            equations += first to second
        }

        var res = 0L

        equations.forEach { (first, second) ->
            val (ax, bx, r1) = first
            val (ay, by, r2) = second

            val a = (r2 * bx - r1 * by).toDouble() / (ay * bx - ax * by)
            val b = (r1 - ax * a) / bx

            if (floor(a) == a && floor(b) == b) {
                res += a.toLong() * 3 + b.toLong()
            }
        }

        return res
    }

    fun part2(input: List<String>): Long {
        val equations = mutableListOf<Pair<Eq, Eq>>()
        input.windowed(4, 4, true) {
            val (ax, ay) = it[0].removePrefix("Button A: ").split(", ")
            val (bx, by) = it[1].removePrefix("Button B: ").split(", ")
            val (r1, r2) = it[2].removePrefix("Prize: ").split(", ")
            val first = Eq(ax.removePrefix("X+").toLong(), bx.removePrefix("X+").toLong(), 10000000000000 + r1.removePrefix("X=").toLong())
            val second = Eq(ay.removePrefix("Y+").toLong(), by.removePrefix("Y+").toLong(), 10000000000000 + r2.removePrefix("Y=").toLong())
            equations += first to second
        }

        var res = 0L

        equations.forEach { (first, second) ->
            val (ax, bx, r1) = first
            val (ay, by, r2) = second

            val a = (r2 * bx - r1 * by).toDouble() / (ay * bx - ax * by)
            val b = (r1 - ax * a) / bx

            if (floor(a) == a && floor(b) == b) {
                res += a.toLong() * 3 + b.toLong()
            }
        }

        return res
    }

    val testInput = readInput("Day${DAY}_test")
    checkAnMeasureTime(480) { part1(testInput) }
    checkAnMeasureTime(875318608908) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(34787) { part1(input) }
    checkAnMeasureTime(85644161121698) { part2(input) }
}
