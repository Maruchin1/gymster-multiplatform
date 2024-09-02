package com.maruchin.gymster.core.utils

fun <T> Collection<T>.updated(index: Int, function: (T) -> T) = mapIndexed { i, item ->
    if (i == index) function(item) else item
}

fun <T> Collection<T>.updated(predicate: (T) -> Boolean, function: (T) -> T) = map { item ->
    if (predicate(item)) function(item) else item
}

fun <T> Collection<T>.swap(fromIndex: Int, toIndex: Int): List<T> {
    val mutableList = toMutableList()
    mutableList.add(toIndex, mutableList.removeAt(fromIndex))
    return mutableList
}
