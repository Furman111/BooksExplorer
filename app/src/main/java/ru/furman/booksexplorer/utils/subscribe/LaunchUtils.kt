package ru.furman.booksexplorer.utils.subscribe

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KMutableProperty0

interface LaunchUtils {

    fun CoroutineScope.launchSingle(
        jobProperty: KMutableProperty0<Job?>?,
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    )

}