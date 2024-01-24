package ru.otus.dz_2024_01.secureprefs

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    /**
     * Master key for encrypting preferences
     * https://blog.kotlin-academy.com/secure-secrets-in-android-using-jetpack-security-in-depth-android-security-02-4026b8e012f4
     */
    @Provides
    @Singleton
    @Named("masterKey")
    fun encryptionKey(): String {
        return MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    }

    @Provides
    @Singleton
    @Named("securePreferences")
    fun securePreferences(context: Application, @Named("masterKey") key: String): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "secureKeyValue",
            key,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    @Named("commonPreferences")
    fun plainPreferences(context: Application): SharedPreferences {
        return context.getSharedPreferences("commonKeyValue", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @Named("secureValues")
    fun userKeyValueStorage(@Named("securePreferences") preferences: SharedPreferences): KeyValueStorage {
        return PreferenceStorage(preferences)
    }

    @Provides
    @Singleton
    @Named("commonValues")
    fun commonKeyValueStorage(@Named("commonPreferences") preferences: SharedPreferences): KeyValueStorage {
        return PreferenceStorage(preferences)
    }
}