package ru.furman.booksexplorer.utils.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KMutableProperty0


fun CoroutineScope.launchSingle(
    jobProperty: KMutableProperty0<Job?>?,
    context: CoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    jobProperty?.get()?.cancel()
    launch(context) {
        block()
    }.also { jobProperty?.set(it) }
}
