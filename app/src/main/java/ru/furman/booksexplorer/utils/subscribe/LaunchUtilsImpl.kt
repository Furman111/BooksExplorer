package ru.furman.booksexplorer.utils.subscribe

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KMutableProperty0

class LaunchUtilsImpl : LaunchUtils {

    override fun CoroutineScope.launchSingle(
        jobProperty: KMutableProperty0<Job?>?,
        context: CoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) {
        jobProperty?.get()?.cancel()
        launch(context) {
            block()
        }.also { jobProperty?.set(it) }
    }

}