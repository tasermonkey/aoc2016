import lib.inputByLine

data class Room(val encryptedName: String, val name: String, val sectorId: Int, val checkSum: String)

fun parseRoom(str: String): Room {
    val test = Regex("([a-z\\-]+)-(\\d+)\\[([a-z]+)\\]")
    val result = test.find(str)!!
    return Room(result.groupValues[1], "", result.groupValues[2].toInt(), result.groupValues[3])

}

fun getMostCommon(room: Room): Set<Char> = room.encryptedName.replace("-", "").toCharArray().groupBy { it }.entries
        .map { it -> Pair(it.key, it.value.size) }.sortedBy { it -> it.first } // ties broke by letter
        .sortedByDescending { it -> it.second }.take(5).map { it.first }.toSet()

fun calcCheckSum(room: Room) = getMostCommon(room).joinToString("")
fun isValid(room: Room) = room.checkSum == calcCheckSum(room)
fun decryptName(name: String, rotateAmount: Int): String {
    val padRng = ('a'..'z')
    val pad = padRng.toList()
    return name.map { it -> if (padRng.contains(it)) pad[(pad.indexOf(it) + rotateAmount) % pad.size] else ' ' }.joinToString("")
}

fun decryptRoom(room: Room): Room = Room(room.encryptedName, decryptName(room.encryptedName, room.sectorId), room.sectorId, room.checkSum)


fun main(args: Array<String>) {
    val rooms = inputByLine(4, 1).map(::parseRoom)
    val validRooms = rooms.filter(::isValid).map(::decryptRoom)
    println("Sum of validRooms sector Ids: ${validRooms.sumBy(Room::sectorId)}")
    println("North pole room: ${validRooms.filter { it.name.contains("north") || it.name.contains("pole") }}")
}