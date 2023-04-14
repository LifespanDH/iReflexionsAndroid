package com.lifespandh.ireflexions.utils.jwt

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lifespandh.ireflexions.di.dataStore
import com.lifespandh.ireflexions.utils.logs.logE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenManager(private val context: Context) {
    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            logE("called jere $token")
            preferences[ACCESS_TOKEN] = token
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
//            preferences.remove(ACCESS_TOKEN)
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN]
        }
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        context.dataStore.edit {
            it[REFRESH_TOKEN] = refreshToken
        }
    }

    suspend fun deleteRefreshToken() {
        context.dataStore.edit {
            it.remove(REFRESH_TOKEN)
        }
    }
}