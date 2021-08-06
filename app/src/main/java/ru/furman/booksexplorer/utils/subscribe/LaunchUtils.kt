package ru.furman.booksexplorer.utils.subscribe

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.reflect.KMutableProperty0

interface LaunchUtils {

    fun CoroutineScope.launchSingle(
        jobProperty: KMutableProperty0<Job?>?,
        block: suspend CoroutineScope.() -> Unit
    )

}