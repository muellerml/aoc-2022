fun main() {
    fun part1(input: List<String>): Int {
        return input.foldIndexed(Calories(0, 0)) { index, calories, addition ->
            when (val addToInt = addition.toIntOrNull()) {
                null -> if (calories.max < calories.current) {
                    println("Highest value found for index $index")
                    calories.copy(max = calories.current, current = 0)
                } else Calories(calories.max, 0)
                else -> calories.copy(current = calories.current + addToInt)
            }
        }.max
    }

    fun part2(input: List<String>): Int {
        val elves: MutableList<Elf> = mutableListOf()
        input.foldIndexed(Elf(0)) { index, elf, calories: String ->
            val addToCalories = calories.toIntOrNull()
            println(index)
            when {
                addToCalories == null -> {
                    elves.add(elf)
                    Elf(0)
                }
                index == (input.size - 1) -> {
                    elves.add(elf.copy(calories = elf.calories + addToCalories))
                    Elf(0)
                };
                else -> elf.copy(calories = elf.calories + addToCalories)
            }
        }
        return elves.sortedByDescending { it.calories }.take(3).sumOf { it.calories }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val result = part1(testInput)
    check(result == 24000)

    val result2 = part2(testInput)
    check(result2 == 45000)

    val input = readInput("Day01")
    println("Part1: " + part1(input))
    println("Part2: " + part2(input))
}


data class Calories(val max: Int, val current: Int)
data class Elf(val calories: Int)
