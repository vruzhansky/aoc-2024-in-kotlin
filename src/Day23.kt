private const val DAY = "23"

fun main() {

    fun parse(input: List<String>): Map<String, Set<String>> {
        val nodes = input.map { line -> line.split("-") }
        val graph = buildMap<String, Set<String>> {
            nodes.forEach { node ->
                merge(node.first(), setOf(node.last())) { a, b -> a + b }
                merge(node.last(), setOf(node.first())) { a, b -> a + b }
            }
        }
        return graph
    }

    fun part1(input: List<String>): Int {
        val graph = parse(input)

        val res = buildSet {
            graph.forEach { (vertex, vertices) ->
                vertices.forEach { vertex2 ->
                    vertices.forEach { vertex3 ->
                        if (vertex2 != vertex3
                            && graph[vertex2]!!.contains(vertex) && graph[vertex2]!!.contains(vertex3)
                            && graph[vertex3]!!.contains(vertex) && graph[vertex3]!!.contains(vertex2)
                        ) {
                            add(setOf(vertex, vertex2, vertex3))
                        }
                    }
                }
            }
        }
            .filter { it.any { n -> n.startsWith("t") } }
        return res.size
    }

    fun part2(input: List<String>): String {
        val graph = parse(input)

        var maxClique = mutableSetOf<String>()
        graph.forEach { (vertex, _) ->
            val clique = mutableSetOf(vertex)
            graph.forEach { (vertex2, _) ->
                if (vertex != vertex2) {
                    if (clique.all { graph[it]!!.contains(vertex2) }) {
                        clique.add(vertex2)
                    }
                }
            }
            if (clique.size > maxClique.size) {
                maxClique = clique
            }
        }

        return maxClique.sorted().joinToString(",")
    }

    val testInput = readInput("Day${DAY}_test")
    checkAnMeasureTime(7) { part1(testInput) }
    checkAnMeasureTime("co,de,ka,ta") { part2(testInput) }

    val input = readInput("Day${DAY}")
    checkAnMeasureTime(1419) { part1(input) }
    checkAnMeasureTime("af,aq,ck,ee,fb,it,kg,of,ol,rt,sc,vk,zh") { part2(input) }
}
