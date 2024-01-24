package ru.otus.net

import android.util.Log
import kotlinx.coroutines.delay
import org.jetbrains.annotations.VisibleForTesting
import ru.otus.domain.data.Address
import ru.otus.domain.net.AddressSuggestService
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class AddressSuggestServiceImpl @Inject constructor() : AddressSuggestService {
    override suspend fun suggest(query: String): List<Address> {
        Log.i(TAG, "Getting addresses for suggest: $query")
        delay(3L.seconds)
        return addresses
            .filter { it.contains(query, ignoreCase = true) }
            .map { Address(it) }
            .also {
                Log.i(TAG, "Found addresses: $it")
            }
    }
}

private const val TAG = "AddressSuggestService"

@VisibleForTesting
internal val addresses = listOf(
    "Moscow, ul. Tverskaya, 1",
    "Moscow, ul. Pokrovka, 2",
    "St. Petersburg, Nevsky Prospekt, 1",
    "Serpuhov, ul. Lenina, 1",
)