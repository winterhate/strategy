package com.winterhate.strategy.api

fun interface Strategy<T> {
    fun apply(data: T)
}

