package util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface Dispatcher {
    suspend fun <T> CoroutineScope.dispatch(f: () -> T): T
}

object IO : Dispatcher {
    override suspend fun <T> CoroutineScope.dispatch(f: () -> T): T = withContext(Dispatchers.IO) { f() }
}