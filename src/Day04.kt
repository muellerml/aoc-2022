fun main() {
    fun part1(input: List<String>): Int {
        return input.count { line ->
            val (leftRange, rightRange) = line.split(",").map { Range(it) }
            if (leftRange.total() > rightRange.total()) {
                leftRange.max >= rightRange.max && leftRange.min <= rightRange.min
            } else {
                rightRange.max >= leftRange.max && rightRange.min <= leftRange.min
            }
        }
    }






    fun part2(input: List<String>): Int {
        return input.count { line ->
            val (leftRange, rightRange) = line.split(",").map { Range(it) }
            (leftRange.max >= rightRange.min && leftRange.max <= rightRange.max) || (rightRange.max >= leftRange.min && rightRange.max <= leftRange.max)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day04_test")
    val result = part1(testInput)
    check(result == 2)


    val input = readInput("day04")
    println("Part1: " + part1(input))

    val result2 = part2(testInput)
    println(result2)
    check(result2 == 4)

    println("Part2: " + part2(input))
}

data class Range(val min: Int, val max: Int) {
    constructor(rangeString: String): this(rangeString.substringBefore("-").toInt(), rangeString.substringAfter("-").toInt())

    fun total() = max - min
}
