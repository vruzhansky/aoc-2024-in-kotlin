package utils

data class TrieNode(
    val children: MutableMap<Char, TrieNode> = mutableMapOf(),
    val content: String = "",
    var isWord: Boolean = false,
)

class TrieTree(val root: TrieNode = TrieNode()) {

    fun insert(word: String) {
        var current = root
        word.forEach { char ->
            current = current.children.getOrPut(char) { TrieNode(content = current.content + char) }
        }
        current.isWord = true
    }

    fun contains(word: String): Boolean {
        var current: TrieNode = root
        word.forEach { char ->
            val node = current.children[char] ?: return false
            current = node
        }
        return current.isWord
    }
}