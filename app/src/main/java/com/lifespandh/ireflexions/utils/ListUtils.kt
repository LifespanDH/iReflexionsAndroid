package com.lifespandh.ireflexions.utils

fun <T> MutableList<T>.removeOrAdd(that: T) {
    val index = this.indexOf(that)
    if (index >= 0)
        this.removeAt(index)
    else this.add(that)
}