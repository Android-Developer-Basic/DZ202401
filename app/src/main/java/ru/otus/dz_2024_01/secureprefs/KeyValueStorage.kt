package ru.otus.dz_2024_01.secureprefs

interface KeyValueStorage {
    fun get(key: String): String?
    fun put(key: String, value: String?)
    fun cleanup()
}