import java.util.*
import kotlin.collections.ArrayList


class Graph(private val size: Int) {
    private val adj = ArrayList<ArrayList<Int>>(ArrayList(size))

    init {
        for (i in 0 until size) {
            adj.add(ArrayList())
        }
    }

    fun addEdge(v: Int, w: Int) {
        adj[v].add(w)
    }

    private fun topologicalSortDfs(v: Int, visited: ArrayList<Int>, stack: Stack<Int>): Boolean {
        visited[v] = 1

        for (i in adj[v]) {
            if (visited[i] == 1) {
                return false
            }
            if (visited[i] == 0) {
                if (!topologicalSortDfs(i, visited, stack)) {
                    return false
                }
            }
        }
        visited[v] = 2
        stack.push(v)
        return true
    }

    fun topologicalSort() {
        val stack = Stack<Int>()
        val visited = ArrayList<Int>(size)
        for (i in 0 until size) {
            visited.add(0)
        }

        for (i in 0 until size) {
            if (visited[i] == 0) {
                if (!topologicalSortDfs(i, visited, stack)) {
                    println("Impossible")
                    return
                }
            }
        }

        while (!stack.empty()) {
            print('a' + stack.pop() + " ")
        }

    }
}

fun checkIChar(i: Int, g: Graph, names: MutableList<String>): Boolean {
    var currentChar = '0'
    var fromIndex = 0
    var toIndex = 0

    for (name in names) {
        if (name.length <= i) {
            if (currentChar != '0') {
                return false
            }
            fromIndex = toIndex
        } else if (name[i] != currentChar) {
            if (currentChar != '0') {
                g.addEdge(currentChar - 'a', name[i] - 'a')
            }
            currentChar = name[i]
            if (toIndex - fromIndex > 2) {
                if (!checkIChar(i + 1, g, names.subList(fromIndex, toIndex))) {
                    return false
                }
            }
            fromIndex = toIndex
        }
        toIndex++
    }
    if (toIndex - fromIndex > 2) {
        if (!checkIChar(i + 1, g, names.subList(fromIndex, toIndex))) {
            return false
        }
    }
    return true
}

// TODO тесты

fun main() {
    val input = Scanner(System.`in`)
    val n = input.nextInt()
    input.nextLine()
    val names = ArrayList<String>(n)

    for (i in 0 until n) {
        names.add(input.nextLine())
    }
    val g = Graph(26)
    if (!checkIChar(0, g, names)) {
        println("Impossible")
        return
    }
    g.topologicalSort()
}
