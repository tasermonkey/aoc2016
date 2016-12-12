import lib.column
import lib.inputByLine
import lib.rotate
import lib.rotateLeft
import java.util.*

internal val ON = "\u2588"
internal val OFF = "\u2591"

class Screen(val width: Int, val height: Int) {
    val display: Array<Array<Boolean>> = Array(height, { Array(width, { false }) })

    val asDisplayString: String
            get() = display.joinToString("\n") { it.joinToString(""){l -> if (l) ON else OFF} }

    fun printScreen() {
        println(asDisplayString)
        println("Number Lit: $numberLit")
        println()
    }

    val numberLit: Int
        get() = display.sumBy { it.count { it } }
}

interface LedCmd {
    fun execute(screen: Screen): Screen
}

internal data class LedRectCmd(val width: Int, val height: Int) : LedCmd {
    override fun execute(screen: Screen): Screen {
        (0..height-1).forEach { y ->
            (0..width-1).forEach { x ->
                screen.display[y][x] = true
            }
        }
        return screen
    }
}

internal data class LedRotRowCmd(val row: Int, val magnitude: Int) : LedCmd {
    override fun execute(screen: Screen): Screen {
        screen.display[row].rotateLeft(magnitude)
        return screen
    }
}

internal data class LedRotColCmd(val column: Int, val magnitude: Int) : LedCmd {
    override fun execute(screen: Screen): Screen {
        screen.display.column(column).rotate(magnitude)
        return screen
    }
}

internal val rectCmdLineRE = Regex("rect (\\d+)x(\\d+)")
internal val rotRowCmdLineRE = Regex("rotate row y=(\\d+) by (\\d+)")
internal val rotColLineRE = Regex("rotate column x=(\\d+) by (\\d+)")

internal fun parseRectCmd(line: String) = rectCmdLineRE.matchEntire(line)?.let {
    LedRectCmd(it.groupValues[1].toInt(), it.groupValues[2].toInt())
}

internal fun parseRotRowCmd(line: String) = rotRowCmdLineRE.matchEntire(line)?.let {
    LedRotRowCmd(it.groupValues[1].toInt(), it.groupValues[2].toInt())
}

internal fun parseRotColCmd(line: String) = rotColLineRE.matchEntire(line)?.let {
    LedRotColCmd(it.groupValues[1].toInt(), it.groupValues[2].toInt())
}

fun parseLedCmd(line: String) = arrayOf(::parseRectCmd, ::parseRotRowCmd, ::parseRotColCmd).mapNotNull { it(line) }.firstOrNull()

fun main(args: Array<String>) {
    val screen = Screen(50, 6)
    //val screen = Screen(7, 3)
    val cmds = inputByLine(8, 1).map(::parseLedCmd)
    //println(cmds.mapIndexed { i, ledCmd -> "${i + 1} $ledCmd" }.joinToString("\n"))
    println("Screen(${screen.width}x${screen.height})")
    cmds.forEach { cmd -> cmd?.execute(screen) }
    screen.printScreen()
}