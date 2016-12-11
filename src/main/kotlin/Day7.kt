import lib.inputByLine
import lib.window

class Ip7(val line: String) {
    val ipParts: List<String> by lazy { line.split('[', ']') }
    val superNets: List<String> by lazy {
        ipParts.filterIndexed { i, s -> i % 2 == 0 }
    }

    val hyperNets: List<String> by lazy {
        ipParts.filterIndexed { i, s -> i % 2 != 0 }
    }

    val supportsTLS: Boolean by lazy {
        superNets.any(::containsABBA) && hyperNets.none(::containsABBA)
    }

    val supportsSSL: Boolean by lazy {
        val abas = superNets.flatMap(::areaBroadcastAccessors)
        val babsPossible = hyperNets.flatMap(::areaBroadcastAccessors)
        babsPossible.any { abas.any { a -> a[0] == it[1] && a[1] == it[0] } }
    }

    override fun toString(): String = line
}

internal fun containsAutonomousBridgeBypassAnnotation(str: String): Boolean =
        str.toCharArray().asList().window(4, 1)
                .any { it[0] != it[1] && it.subList(0, 2) == it.subList(2, 4).reversed() }

internal fun containsABBA(str: String) = containsAutonomousBridgeBypassAnnotation(str)


internal fun areaBroadcastAccessors(str: String): List<String> =
        str.toCharArray().asList().window(3, 1)
                .filter { it[0] == it[2] && it[1] != it[0] }
                .map { it.joinToString("") }.toList()

fun main(args: Array<String>) {
    val ip7s = inputByLine(7, 1).map(::Ip7)

    println("Number of Ip7 that support TLS: ${ip7s.count(Ip7::supportsTLS)}")
    println("Number of Ip7 that support SSL: ${ip7s.count(Ip7::supportsSSL)}")
}