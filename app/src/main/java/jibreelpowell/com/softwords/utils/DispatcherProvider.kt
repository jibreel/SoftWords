package jibreelpowell.com.softwords.utils

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val computation: CoroutineDispatcher
    val io: CoroutineDispatcher
    val ui: CoroutineDispatcher
}