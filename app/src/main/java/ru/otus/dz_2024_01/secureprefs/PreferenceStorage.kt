package ru.otus.dz_2024_01.secureprefs

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Implementation of [KeyValueStorage] based on [SharedPreferences]
 */
class PreferenceStorage(private val preferences: SharedPreferences) : KeyValueStorage {
    override fun get(key: String): String? {
        return preferences.getString(key, null)
    }

    override fun put(key: String, value: String?) {
        if (null == value) {
            preferences.edit { remove(key) }
        } else {
            preferences.edit { putString(key, value) }
        }
    }

    override fun cleanup() {
        preferences.edit { clear() }
    }
}