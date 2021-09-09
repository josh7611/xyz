package com.xyz.app.data.db

import android.view.View
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend inline fun <T> call(
    ioDispatcher: CoroutineDispatcher,
    crossinline daoCall: suspend () -> T
): T {
    return executeDaoCall(ioDispatcher, daoCall)
}

suspend inline fun <T> executeDaoCall(
    ioDispatcher: CoroutineDispatcher,
    crossinline daoCall: suspend () -> T
): T {
    return withContext(ioDispatcher) {
        try {
            return@withContext daoCall()
        } catch (e: Exception) {
            // todo: Convert DB specified Exceptions into Application Exceptions which defined by PM or designer
            throw e
        }
    }
}

fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}
