import org.apache.commons.codec.digest.DigestUtils.md5Hex
import java.time.Duration
import kotlin.system.measureTimeMillis

class DoorPassword(val id: String) {
    val password: String by lazy {

        interestingHashSequence
                .take(8)
                .map { it[5] }
                .joinToString("")
    }

    val secondPassword: String by lazy {
        interestingHashSequence.filter { it[5] >= '0' && it[5] < '8' }
                .distinctBy { it[5] }
                .take(8)
                .fold(kotlin.arrayOfNulls<Char>(8)) {
                    calcedPassword, hash ->
                    calcedPassword[hash[5] - '0'] = hash[6] // hash[5].toInt() didn't seem to do 'atoi' unlike what String.toInt() does
                    calcedPassword
                }
                .joinToString("")
    }

    private val interestingHashSequence:  Sequence<String> by lazy {
        generateSequence(0, Int::inc)
                .map { md5Hex("$id$it".toByteArray()) }
                .filter { it.startsWith("00000") }
    }
}

fun main(args: Array<String>) {
    val id = "abbhdwsy";
    val doorPass = DoorPassword(id)
    val calcTime = measureTimeMillis {
        println("first door password for ${doorPass.id}: ${doorPass.password}")
        println("second door password for ${doorPass.id}: ${doorPass.secondPassword}")
    }
    println("Total time spent: ${Duration.ofMillis(calcTime).toString()}")
}