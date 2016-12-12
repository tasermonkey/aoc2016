import lib.Position
import lib.fullInput

internal enum class Direction {
    North {
        override fun x() = 0
        override fun y() = 1
        override fun spin(dir: String) = if (dir == "R") Direction.East else Direction.West
    },
    South {
        override fun x() = 0
        override fun y() = -1
        override fun spin(dir: String) = if (dir == "R") Direction.West else Direction.East
    },
    East {
        override fun x() = -1
        override fun y() = 0
        override fun spin(dir: String) = if (dir == "R") Direction.South else Direction.North
    },
    West {
        override fun x() = 1
        override fun y() = 0
        override fun spin(dir: String) = if (dir == "R") Direction.North else Direction.South
    };

    abstract fun x(): Int
    abstract fun y(): Int
    abstract fun spin(dir: String): Direction
}

internal data class Person(val pos: Position, val facing: Direction)
internal data class Cmd(val spin: String, val magnitude: Int)
internal fun dist(person: Person) = Math.abs(person.pos.x) + Math.abs(person.pos.y)

fun main(args: Array<String>) {
    val strInput = fullInput(1, 1)
    val input = strInput.split(",").map(String::trim)
            .map({ it ->
                Cmd(it.substring(0, 1), it.substring(1).toInt())
            })
    part1(input)
    part2(input)
}

internal fun part1(input: List<Cmd>) {
    val result = input.fold(Person(Position(0, 0), Direction.North)) { p, i ->
        val dir = p.facing.spin(i.spin)
        val loc = Position(p.pos.x + dir.x() * i.magnitude, p.pos.y + dir.y() * i.magnitude)
        Person(loc, dir)
    }
    println("End of path: $result, shortest distance: ${dist(result)}")
}

internal fun part2(input: List<Cmd>) {
    val visited = mutableSetOf(Position(0, 0))
    var p = Person(Position(0, 0), Direction.North)
    input.any({i ->
        val dir = p.facing.spin(i.spin)
        var loc = p.pos
        val result = (1..i.magnitude).any { it ->
            loc = Position(loc.x + dir.x(), loc.y + dir.y())
            !visited.add(loc)
        }
        p = Person(loc, dir)
        result
    })
    println("Already visited this place $p, shortest distance: ${dist(p)}")
}