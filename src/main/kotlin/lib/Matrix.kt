package lib

fun <T : Any> Array<Array<T>>.column(colIndex: Int): MutableList<T> = ColumnView(this, colIndex)

internal class ColumnViewIterator<T>(val view: ColumnView<T>, start: Int = 0) : MutableListIterator<T> {
    private var cur = start
    override fun hasNext(): Boolean = cur != view.size

    override fun hasPrevious(): Boolean = cur != 0

    override fun next(): T = view[cur++]

    override fun nextIndex(): Int = cur

    override fun previous(): T = view[--cur]

    override fun previousIndex(): Int = cur - 1

    override fun set(element: T) {
        throw UnsupportedOperationException("not implemented")
    }

    override fun add(element: T) {
        throw UnsupportedOperationException("not implemented")
    }

    override fun remove() {
        throw UnsupportedOperationException("not implemented")
    }

}

internal class ColumnView<T>(val inner: Array<Array<T>>, val column: Int) : MutableList<T> {
    override fun set(index: Int, element: T): T {
        val t = inner[index][column]
        inner[index][column] = element
        return t
    }

    override val size: Int
        get() = inner.size

    override fun contains(element: T): Boolean = inner.any { (it[column]?.equals(element)) ?: false }

    override fun containsAll(elements: Collection<T>): Boolean = elements.all { contains(it) }

    override fun get(index: Int): T = inner[index][column]

    override fun indexOf(element: T): Int = inner.indexOfFirst { element?.equals(it[column]) ?: false }

    override fun isEmpty(): Boolean = inner.isEmpty()

    override fun iterator(): MutableIterator<T> = listIterator()

    override fun lastIndexOf(element: T): Int = inner.indexOfLast { element?.equals(it[column]) ?: false }

    override fun listIterator(): MutableListIterator<T> = listIterator(0)

    override fun listIterator(index: Int): MutableListIterator<T> = ColumnViewIterator(this, index)

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        throw UnsupportedOperationException("not implemented")
    }

    override fun add(element: T): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(index: Int, element: T) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addAll(elements: Collection<T>): Boolean {
        throw UnsupportedOperationException("not implemented")
    }

    override fun clear() {
        throw UnsupportedOperationException("not implemented")
    }

    override fun remove(element: T): Boolean {
        throw UnsupportedOperationException("not implemented")
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        throw UnsupportedOperationException("not implemented")
    }

    override fun removeAt(index: Int): T {
        throw UnsupportedOperationException("not implemented")
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        throw UnsupportedOperationException("not implemented")
    }

}

fun <T : Any> Array<T>.rotate(amount: Int): Array<T> {
    this.map { it } .forEachIndexed { i, t -> this[(i + amount) % size] = t }
    return this
}

fun <T : Any> MutableList<T>.rotate(amount: Int): MutableList<T> {
    this.map { it } .forEachIndexed { i, t -> this[(i + amount) % size] = t }
    return this
}

internal fun minux(cur: Int, amount: Int, size: Int): Int {
    val v = cur - amount
    return if (v < 0) size + v else v
}

fun <T : Any> Array<T>.rotateLeft(amount: Int): Array<T> {
    this.mapIndexed { i, t -> this[minux(i, amount, size)] }.forEachIndexed { i, t -> this[i] = t }
    return this
}

fun <T : Any> MutableList<T>.rotateLeft(amount: Int): MutableList<T> {
    this.mapIndexed { i, t -> this[minux(i, amount, size)] }.forEachIndexed { i, t -> this[i] = t }
    return this
}