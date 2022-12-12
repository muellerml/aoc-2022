fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val split = line.count() / 2
            val first = line.take(split)
            val last = line.takeLast(split)
            val character = first.first { last.contains(it) }
            val value = character.getValue()
            value
        }
    }






    fun part2(input: List<String>): Int {
        return input.windowed(3, step = 3).sumOf { list ->
            val first = list[0]
            first.first { list[1].contains(it) && list[2].contains(it) }.getValue()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day03_test")
    val result = part1(testInput)
    check(result == 157)


    val input = readInput("day03")
    println("Part1: " + part1(input))

    val result2 = part2(testInput)
    check(result2 == 70)

    println("Part2: " + part2(input))
}
fun Char.getValue() = if (this.isUpperCase()) this.minus('A') + 27 else this.minus('a') + 1
