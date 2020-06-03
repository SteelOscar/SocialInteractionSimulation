package di

interface DiProvider<T> {

    fun provide(): T
}