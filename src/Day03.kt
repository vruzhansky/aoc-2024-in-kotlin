private const val DAY = "03"

fun main() {

    fun part1(input: List<String>): Int {
        val rex = Regex("mul\\((\\d+),(\\d+)\\)")

        var total = 0
        rex.findAll(input.joinToString(separator = ""))
            .map { it.groupValues }
            .forEach { (_, arg1, arg2) -> total += arg1.toInt() * arg2.toInt() }
        return total
    }

    fun part2(input: List<String>): Int {
        val rex = Regex("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)")

        var total = 0
        var doIt = true
        rex.findAll(input.joinToString(separator = "")).forEach { res ->
            val vals = res.groupValues
            val cmd = vals[0]
            when {
                cmd.startsWith("mul") && doIt -> total += vals[1].toInt() * vals[2].toInt()
                cmd.startsWith("don't") -> doIt = false
                cmd.startsWith("do") -> doIt = true
            }
        }
        return total
    }

    val testInput = readInput("Day${DAY}_test")
//    checkAnMeasureTime(161) { part1(testInput) }
    checkAnMeasureTime(48) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(188192787) { part1(input) }
    checkAnMeasureTime(113965544) { part2(input) }
}
