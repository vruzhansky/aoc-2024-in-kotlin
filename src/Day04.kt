private const val DAY = "04"

fun main() {

    fun combos(r: Int, c: Int, chars: List<List<Char>>): Int {
        val rows = chars.size
        val cols = chars[0].size
        var total = 0

        if (r > 2 && "${chars[r - 1][c]}${chars[r - 2][c]}${chars[r - 3][c]}" == "MAS") total++
        if (r < rows - 3 && "${chars[r + 1][c]}${chars[r + 2][c]}${chars[r + 3][c]}" == "MAS") total++

        if (c > 2 && "${chars[r][c - 1]}${chars[r][c - 2]}${chars[r][c - 3]}" == "MAS") total++
        if (c < cols - 3 && "${chars[r][c + 1]}${chars[r][c + 2]}${chars[r][c + 3]}" == "MAS") total++

        if (c > 2 && r > 2 && "${chars[r - 1][c - 1]}${chars[r - 2][c - 2]}${chars[r - 3][c - 3]}" == "MAS") total++
        if (c > 2 && r < rows - 3 && "${chars[r + 1][c - 1]}${chars[r + 2][c - 2]}${chars[r + 3][c - 3]}" == "MAS") total++

        if (c < cols - 3 && r > 2 && "${chars[r - 1][c + 1]}${chars[r - 2][c + 2]}${chars[r - 3][c + 3]}" == "MAS") total++
        if (c < cols - 3 && r < rows - 3 && "${chars[r + 1][c + 1]}${chars[r + 2][c + 2]}${chars[r + 3][c + 3]}" == "MAS") total++

        return total
    }

    fun part1(input: List<String>): Int {
        val chars = input.map { it.toCharArray().toList() }
        var total = 0
        chars.indices.forEach { r ->
            chars[r].indices.forEach { c ->
                if (chars[r][c] == 'X') total += combos(r, c, chars)
            }
        }
        return total
    }

    fun combos2(r: Int, c: Int, chars: List<List<Char>>): Int {
        val rows = chars.size
        val cols = chars[0].size
        var total = 0

        if (c > 0 && c < cols - 1 && r > 0 && r < rows - 1) {
            val first = "${chars[r - 1][c - 1]}${chars[r][c]}${chars[r + 1][c + 1]}"
            val second = "${chars[r + 1][c - 1]}${chars[r][c]}${chars[r - 1][c + 1]}"
            if (("MAS" == first || "SAM" == first) && ("MAS" == second || "SAM" == second)) total++
        }
        return total
    }

    fun part2(input: List<String>): Int {
        val chars = input.map { it.toCharArray().toList() }
        var total = 0
        chars.indices.forEach { r ->
            chars[r].indices.forEach { c ->
                if (chars[r][c] == 'A') total += combos2(r, c, chars)
            }
        }
        return total
    }

    val testInput = readInput("Day${DAY}_test")
//    check(part1(testInput).also { println(it) } == 18)
    check(part2(testInput).also { println(it) } == 9)

    val input = readInput("Day${DAY}")
    check(part1(input).also { println(it) } == 2468)
    check(part2(input).also { println(it) } == 1864)
}
