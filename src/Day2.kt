import lib.Position

class Keypad(val keyPad: Array<Array<String>>) {
    val maxY = keyPad.size - 1
    val maxX = keyPad[0].size - 1
    fun move(pos: Position, dir: MoveDirection): Position {
        return when (dir) {
            MoveDirection.U -> if (pos.y > 0 && keyPad[pos.y - 1][pos.x] != "x") Position(pos.x, pos.y - 1) else pos
            MoveDirection.D -> if (pos.y < maxY && keyPad[pos.y + 1][pos.x] != "x") Position(pos.x, pos.y + 1) else pos
            MoveDirection.L -> if (pos.x > 0 && keyPad[pos.y ][pos.x - 1] != "x")  Position(pos.x - 1, pos.y) else pos
            MoveDirection.R -> if (pos.x < maxX && keyPad[pos.y][pos.x + 1] != "x") Position(pos.x + 1, pos.y) else pos
        }
    }
    fun valueAt(pos: Position) = keyPad[pos.y][pos.x]
}

enum class MoveDirection {
    U,D,L,R
}

fun main(args: Array<String>) {
    part1()
    part2()

}

fun calcCode(keypad: Keypad, startPosition:Position, lines: List<String>): String {
    var position = startPosition
    val code: List<String> = lines.map { it ->
        for (x in it) {
            val dir = MoveDirection.valueOf(x.toString())
            position = keypad.move(position, dir)
        }
        keypad.valueAt(position)
    }
    return code.joinToString("")
}

fun part1() {
    val keypadValues = arrayOf(
            arrayOf("x", "x", "x", "x", "x"),
            arrayOf("x", "1", "2", "3", "x"),
            arrayOf("x", "4", "5", "6", "x"),
            arrayOf("x", "7", "8", "9", "x"),
            arrayOf("x", "x", "x", "x", "x")
    )
    val lines = Position::class.java.getResourceAsStream("/day2/p1Input.txt").bufferedReader().use { it.readLines() }
    val keypad = Keypad(keypadValues)
    println(calcCode(keypad, Position(2, 2), lines))
}

fun part2() {
    val keypadValues = arrayOf(
            arrayOf("x", "x", "1", "x", "x"),
            arrayOf("x", "2", "3", "4", "x"),
            arrayOf("5", "6", "7", "8", "9"),
            arrayOf("x", "A", "B", "C", "x"),
            arrayOf("x", "x", "D", "x", "x")
    )
    val lines = Position::class.java.getResourceAsStream("/day2/p2Input.txt").bufferedReader().use { it.readLines() }
    val keypad = Keypad(keypadValues)
    println(calcCode(keypad, Position(0, 2), lines))
}