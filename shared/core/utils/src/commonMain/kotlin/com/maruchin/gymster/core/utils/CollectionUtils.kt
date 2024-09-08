package com.maruchin.gymster.core.utils

fun <T> Collection<T>.updated(index: Int, function: (T) -> T) = mapIndexed { i, item ->
    if (i == index) function(item) else item
}

fun <T> Collection<T>.updated(predicate: (T) -> Boolean, function: (T) -> T) = map { item ->
    if (predicate(item)) function(item) else item
}

fun <T> Collection<T>.removed(index: Int) = filterIndexed { i, _ -> i != index }

fun <T> Collection<T>.removed(predicate: (T) -> Boolean) = filterNot(predicate)

fun <T> Collection<T>.swap(fromIndex: Int, toIndex: Int): List<T> {
    val mutableList = toMutableList()
    mutableList.add(toIndex, mutableList.removeAt(fromIndex))
    return mutableList
}

fun <T> Set<T>.toggle(value: T): Set<T> = if (contains(value)) minus(value) else plus(value)
