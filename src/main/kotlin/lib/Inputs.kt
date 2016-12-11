package lib

fun inputByLine(day: Int, part: Int) =inputByLine("/day${day}/p${part}Input.txt")

fun inputByLine(resource: String) =  Position::class.java.getResourceAsStream(resource).bufferedReader().use { it.readLines() }

fun fullInput(day: Int, part: Int) = fullInput("/day${day}/p${part}Input.txt")
fun fullInput(resource: String) =  Position::class.java.getResourceAsStream(resource).bufferedReader().use { it.readText() }