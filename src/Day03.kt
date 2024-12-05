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
//    check(part1(testInput).also { println(it) } == 161)
    check(part2(testInput).also { println(it) } == 48)

    val input = readInput("Day${DAY}")
    check(part1(input).also { println(it) } == 188192787)
    check(part2(input).also { println(it) } == 113965544)
}
