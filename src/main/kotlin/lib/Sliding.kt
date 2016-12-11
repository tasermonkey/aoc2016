package lib

fun <T> List<T>.window(size: Int,
                          step: Int = size): Sequence<List<T>> =
        partitionRanges(size, step)
                .takeWhile { it.last in indices }
                .map { slice(it) }

internal fun partitionRanges(size: Int,
                             step: Int = size): Sequence<IntRange> =
        generateSequence(0) { it + step }
                .map { it .. it + size - 1 }