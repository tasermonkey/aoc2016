import lib.Position
import lib.batch
import lib.inputByLine

fun main(args: Array<String>) {
    val lines = inputByLine(3, 1)
    val triangles: List<List<Int>> = lines.map { it ->
        it.split(Regex("\\s+"))
    }.map { it -> it.filter(String::isNotEmpty) }.map { it.map(String::toInt) }

    val rotatedTriangles = triangles.asSequence().batch(3).flatMap { it ->
        arrayListOf(arrayListOf(it[0][0], it[1][0], it[2][0]),
                arrayListOf(it[0][1], it[1][1], it[2][1]),
                arrayListOf(it[0][2], it[1][2], it[2][2])
                ).asSequence()
    }

    val possible = triangles.count(::possibleTriangle)
    val rotatedPossible = rotatedTriangles.count(::possibleTriangle)
    println("$possible, $rotatedPossible")
}

fun possibleTriangle(t: List<Int>) = t[0] + t[1] > t[2] && t[1] + t[2] > t[0] && t[2] + t[0] > t[1]
