import kotlin.math.pow

private const val DAY = "17"

fun main() {

    fun runProg(a: Long, prog: List<Int>): List<Int> {
        var rega = a
        var regb = 0L
        var regc = 0L
        val res = mutableListOf<Int>()
        var pointer = 0
        while (pointer < prog.size - 1) {
            val instruction = prog[pointer]
            val operandValue = when (val operand = prog[pointer + 1]) {
                in 0..3 -> operand
                4 -> rega
                5 -> regb
                6 -> regc
                else -> -1
            }.toLong()

            when (instruction) {
                //adv
                0 -> rega = (rega / 2.0.pow(operandValue.toDouble())).toLong()
                //bxl
                1 -> regb = regb.xor(operandValue)
                //bst
                2 -> regb = operandValue % 8
                // jnz
                3 -> if (rega != 0L) {
                    pointer = operandValue.toInt()
                    continue
                }
                //bxc
                4 -> regb = regb.xor(regc)
                //out
                5 -> (operandValue % 8).let { res.add(it.toInt()) }
                //bdv
                6 -> regb = (rega / 2.0.pow(operandValue.toDouble())).toLong()
                //cdv
                7 -> regc = (rega / 2.0.pow(operandValue.toDouble())).toLong()
            }
            pointer += 2
        }
        return res
    }

    fun part1(input: List<String>): String {
        val (registers, program) = input.partition { it.startsWith("Register") || it.isBlank() }

        val rega = registers[0].split(": ").last().toLong()
        val prog = program.joinToString("").removePrefix("Program: ").split(",").map { it.toInt() }

       val res = runProg(rega, prog)

        return res.joinToString(",")
    }

    fun testProg(loopBody: List<Int>, target: List<Int>, rega: Long): Long {
        if (target.isEmpty()) return rega
        val nextRega = rega * 8
        for (remainder in 0..7) {
            val candidate = nextRega + remainder
            if (runProg(candidate, loopBody).first() == target.last()) {
                println("Candidate $candidate,  results in ${target.last()}, lvl ${target.size}")
                try {
                    return testProg(loopBody, target.dropLast(1), if (candidate == 0L) 1L else candidate)
                } catch (e: Exception) {
                    continue
                }
            }
        }
        println("Failed,  lvl ${target.size} ")
        error("Candidate not found")
    }

    fun part2(input: List<String>): Long {
        val (_, program) = input.partition { it.startsWith("Register") || it.isBlank() }

        val prog = program.joinToString("").removePrefix("Program: ").split(",").map { it.toInt() }
        val progBody = prog.dropLast(2)
        val res = testProg(progBody, prog, 0)

        println(runProg(res, prog))
        return res
    }

    val testInput = readInput("Day${DAY}_test")
//    checkAnMeasureTime("4,6,3,5,6,3,5,2,1,0") { part1(testInput) }
    checkAnMeasureTime(117440) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime("1,5,3,0,2,5,2,5,3") { part1(input) }
    checkAnMeasureTime(108107566389757) { part2(input) }
}
