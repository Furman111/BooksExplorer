package ru.furman.booksexplorer.utils.coroutine.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

data class Dispatchers(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val computation: CoroutineDispatcher
)