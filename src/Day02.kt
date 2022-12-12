fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val splitted = it.split(" ")
            calculateResult(OpponentSymbol.valueOf(splitted[0]).translate(), OwnSymbol.valueOf(splitted[1]))
        }
    }



    fun part2(input: List<String>): Int {
        return input.sumOf {
            val splitted = it.split(" ")
            calculateSymbolAndResult(OpponentSymbol.valueOf(splitted[0]), OwnSymbol.valueOf(splitted[1]))
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02_test")
    val result = part1(testInput)
    check(result == 15)

    val result2 = part2(testInput)
    check(result2 == 12)

    val input = readInput("day02")
    println("Part1: " + part1(input))
    println("Part2: " + part2(input))
}

enum class OpponentSymbol(val typ: String) {
    A("stein"),
    B("papier"),
    C("schere");
    fun translate(): OwnSymbol {
        return when (this) {
            A -> OwnSymbol.X
            B -> OwnSymbol.Y
            C -> OwnSymbol.Z
        }
    }
}
enum class OwnSymbol(val typ: String,val value: Int) {
    X("stein", 1),
    Y("papier", 2),
    Z("schere", 3)
}


sealed class Outcome(val value: Int, val expectedOutcome: String) {
    object LOSS: Outcome(0, "X")
    object DRAW: Outcome(3, "Y")
    object WIN: Outcome(6, "Z")
}

fun calculateResult(opponent: OwnSymbol, own: OwnSymbol): Int {
    val outcome : Outcome = when (opponent) {
        own -> Outcome.DRAW
        OwnSymbol.X -> if (own == OwnSymbol.Y) Outcome.WIN else Outcome.LOSS
        OwnSymbol.Y -> if (own == OwnSymbol.X) Outcome.LOSS else Outcome.WIN
        OwnSymbol.Z -> if (own == OwnSymbol.X) Outcome.WIN else Outcome.LOSS
        else -> error("new case detected: $opponent to $own")
    }
    println("Outcome: $outcome")
    println("Symbol: $own")
    return outcome.value + own.value
}

fun calculateSymbolAndResult(opponent: OpponentSymbol, own: OwnSymbol): Int {
    val outcome = when (own.name) {
        Outcome.DRAW.expectedOutcome -> Outcome.DRAW
        Outcome.LOSS.expectedOutcome -> Outcome.LOSS
        Outcome.WIN.expectedOutcome -> Outcome.WIN
        else -> error("unexpected symbol $own")
    }

    val chosenSymbol = when (outcome) {
        Outcome.DRAW -> opponent.translate()
        Outcome.WIN -> when (opponent) {
            OpponentSymbol.A -> OwnSymbol.Y
            OpponentSymbol.B -> OwnSymbol.Z
            OpponentSymbol.C -> OwnSymbol.X
        }
        Outcome.LOSS -> when(opponent) {
            OpponentSymbol.A -> OwnSymbol.Z
            OpponentSymbol.B -> OwnSymbol.X
            OpponentSymbol.C -> OwnSymbol.Y
        }
        else -> error("Unexpected symbol $opponent")
    }

    return outcome.value + chosenSymbol.value
}
