private const val DAY = "11"

fun main() {

    data class Num(val num: Long, val lvl: Int)

    fun calc(value: Long, lvl: Int, cache: MutableMap<Num, Long>): Long =
        if (lvl == 0) {
            1
        } else {
            cache.getOrPut(Num(value, lvl)) {
                when {
                    value == 0L -> calc(1L, lvl - 1, cache)
                    value.toString().length % 2 == 0 -> {
                        val strNum = value.toString()
                        val mid = strNum.length / 2
                        calc(strNum.substring(0, mid).toLong(), lvl - 1, cache) +
                                calc(strNum.substring(mid).toLong(), lvl - 1, cache)
                    }

                    else -> calc(value * 2024, lvl - 1, cache)
                }
            }
        }

    fun part1(input: List<String>): Long {
        val arr = input.first().split(" ").map { it.toLong() }

        val cache = mutableMapOf<Num, Long>()
        return arr.fold(0L) { acc: Long, l: Long ->
            acc + calc(l, 25, cache)
        }
    }

    fun part2(input: List<String>): Long {
        val arr = input.first().split(" ").map { it.toLong() }

        val cache = mutableMapOf<Num, Long>()
        return arr.fold(0L) { acc: Long, l: Long ->
            acc + calc(l, 75, cache)
        }
    }

    val testInput = readInput("Day${DAY}_test")
    checkAnMeasureTime(55312) { part1(testInput) }
//    checkAnMeasureTime(0) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(197157) { part1(input) }
    checkAnMeasureTime(234430066982597) { part2(input) }
}
