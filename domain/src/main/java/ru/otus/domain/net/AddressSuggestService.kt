package ru.otus.domain.net

import ru.otus.domain.data.Address

interface AddressSuggestService {
    suspend fun suggest(query: String): List<Address>
}