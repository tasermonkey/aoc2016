import lib.inputByLine

internal fun String.alphaFreq(): Array<Int> = this.fold(Array<Int>(26, {i -> 0}), {freq, c -> freq[c - 'a']++; freq})

internal fun String.mostFreq(): Char {
    val freqs = this.alphaFreq()
    return 'a' + freqs.indices.maxBy { freqs[it] }!!
}

internal fun String.leastFreq(): Char {
    val freqs = this.alphaFreq()
    return 'a' + freqs.indices.minBy { freqs[it] }!!
}

internal fun largestLine(lines: List<String>): Int {
    return lines.maxBy(String::length)!!.length
}

fun main(args: Array<String>) {
    val lines = inputByLine(6, 1)
    val byCols = lines.fold(Array<StringBuffer>(largestLine(lines), {i -> StringBuffer(lines.size) })) {
        cols, line -> line.forEachIndexed { i, c -> cols[i].append(c) }; cols
    }.map(StringBuffer::toString)
    println("First Message: ${byCols.map(String::mostFreq).joinToString("")}")
    println("Second Message: ${byCols.map(String::leastFreq).joinToString("")}")
}
