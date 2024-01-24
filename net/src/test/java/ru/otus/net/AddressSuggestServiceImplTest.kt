package ru.otus.net

import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class AddressSuggestServiceImplTest {
    private val service = AddressSuggestServiceImpl()

    @Test
    fun suggest() = runTest {
        val result = service.suggest("Moscow")
        assertEquals(2, result.size)
        assertEquals(addresses[0], result[0].value)
        assertEquals(addresses[1], result[1].value)
    }
}