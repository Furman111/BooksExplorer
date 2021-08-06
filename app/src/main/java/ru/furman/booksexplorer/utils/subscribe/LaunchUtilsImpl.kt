package ru.furman.booksexplorer.utils.subscribe

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.reflect.KMutableProperty0

class LaunchUtilsImpl : LaunchUtils {

    override fun CoroutineScope.launchSingle(
        jobProperty: KMutableProperty0<Job?>?,
        block: suspend CoroutineScope.() -> Unit
    ) {
        jobProperty?.get()?.cancel()
        launch {
            block()
        }.also { jobProperty?.set(it) }
    }

}