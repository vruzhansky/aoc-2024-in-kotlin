import java.util.*

private const val DAY = "09"

fun main() {

    data class Block(val id: Int, val size: Int)

    fun parse(input: List<String>): Pair<Int, MutableList<Block>> {
        val blocks = input.joinToString(separator = "").map { it.digitToInt() }
        var i = 0
        var id = 0
        val fileBlocks = ArrayList<Block>()
        while (i < blocks.size) {
            if (i % 2 == 0) {
                fileBlocks.add(Block(id, blocks[i]))
                id++
            } else {
                fileBlocks.add(Block(-1, blocks[i]))
            }
            i++
        }
        return id - 1 to fileBlocks
    }

    fun part1(input: List<String>): Long {
        val (_, blocks) = parse(input)
        val deque = LinkedList(blocks)

        val result = mutableListOf<Int>()
        while (deque.isNotEmpty()) {
            val block = deque.pollFirst()
            if (block.id != -1) {
                repeat(block.size) { result.add(block.id) }
                continue
            }
            if (deque.isNotEmpty()) {
                val lastBlock = deque.pollLast()
                if (lastBlock.id == -1) {
                    deque.offerFirst(block)
                    continue
                }
                when {
                    block.size > lastBlock.size -> {
                        repeat(lastBlock.size) { result.add(lastBlock.id) }
                        deque.offerFirst(block.copy(size = block.size - lastBlock.size))
                    }

                    block.size < lastBlock.size -> {
                        repeat(block.size) { result.add(lastBlock.id) }
                        deque.offerLast(lastBlock.copy(size = lastBlock.size - block.size))
                    }

                    else -> {
                        repeat(lastBlock.size) { result.add(lastBlock.id) }
                    }
                }
            }
        }

        return result.foldIndexed(0) { index, acc, value -> acc + value * index }
    }

    fun findFreeIdx(size: Int, maxIdx: Int, blocks: List<Block>): Int {
        blocks.forEachIndexed { idx, block ->
            if (idx >= maxIdx) return -1
            if (block.id == -1 && block.size >= size) return idx
        }
        return -1
    }

    fun freeAtIdx(idx: Int, size: Int, blocks: MutableList<Block>) {
        blocks[idx] = Block(-1, size)
    }

    fun putAtIdx(idx: Int, block: Block, blocks: MutableList<Block>) {
        val current = blocks[idx]
        val leftSpace = current.size - block.size
        blocks[idx] = block
        if (leftSpace > 0) {
            blocks.add(idx + 1, Block(-1, leftSpace))
        }
    }

    fun part2(input: List<String>): Long {
        val (id, blocks) = parse(input)

        var currentFileId = id
        while (currentFileId > 0) {
            val idx = blocks.indexOfLast { it.id == currentFileId }
            val file = blocks[idx]
            val freeIdx = findFreeIdx(file.size, idx, blocks)
            if (freeIdx != -1) {
                freeAtIdx(idx, file.size, blocks)
                putAtIdx(freeIdx, file, blocks)
            }
            currentFileId--
        }

        val result = mutableListOf<Int>()
        blocks.forEach { file ->
            repeat(file.size) { result.add(file.id) }
        }

        return result.foldIndexed(0) { index, acc, value -> acc + if (value == -1) 0 else value * index }
    }

    val testInput = readInput("Day${DAY}_test")
    checkAnMeasureTime(1928) { part1(testInput) }
    checkAnMeasureTime(2858) { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(6463499258318) { part1(input) }
    checkAnMeasureTime(6493634986625) { part2(input) }
}
