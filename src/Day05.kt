import com.sun.org.apache.xpath.internal.operations.Bool

fun main() {
    fun part1(input: List<String>): String {
        return performMoves(input, reversed = true)
    }

    fun part2(input: List<String>): String {
        return performMoves(input, reversed = false)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day05_test")
    val result = part1(testInput)
    println(result)
    check(result == "CMZ")


    val input = readInput("day05")
    println("Part1: " + part1(input))

    val result2 = part2(testInput)
    println(result2)
    check(result2 == "MCD")

    println("Part2: " + part2(input))
}

data class Move(
    val numTake: Int,
    val from: Int,
    val to: Int,
)

private fun performMoves(input: List<String>, reversed: Boolean): String {
    val stack = input.takeWhile { it.isNotEmpty() }
    val moves = input.takeLastWhile { it.isNotEmpty() }
    val stacks = stack.dropLast(1).map {
        it.windowed(4,4, partialWindows = true).map { it.trim() }
    }
    val sortedStacks = mutableMapOf<Int, List<String>>()
    stacks.forEach {list ->
        list.forEachIndexed { index, s ->
            if (s.isNotEmpty()) {
                val charList = sortedStacks.getOrPut(index) { mutableListOf() }
                val value = s.removePrefix("[").removeSuffix("]")
                sortedStacks[index] = charList + value
            }
        }
    }
    val sorted = sortedStacks.toSortedMap()

    val regex = Regex("""move (\d+) from (\d+) to (\d+)""")

    val parsedMoves = moves.map { regex.find(it) }.map {
        val (numTake, from, to) = it!!.destructured
        Move(numTake.toInt(), from.toInt(), to.toInt())
    }

    val result = parsedMoves.fold(sorted) { sorted, move ->
        val originalFrom = sorted[move.from - 1]!!
        val taken = originalFrom.take(move.numTake)
        sorted.compute(move.from - 1) { _, list -> list!!.drop(move.numTake) }
        sorted.compute(move.to - 1) { _, list -> (if (reversed) taken.reversed() else taken) + list!! }
        val result = sorted
        result
    }

    return result.map { (_, value) -> value.first() }.joinToString("")
}
