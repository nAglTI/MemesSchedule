package com.nagl.memesschedule.data.source.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

class DataStoreRepository (
    private val context: Context,
) {

    val group: Flow<String> = context.dataStore.data.map {
        it[USER_GROUP] ?: ""
    }

    val isUserAuth: Flow<Boolean> = context.dataStore.data.map {
        it[IS_USER_AUTH] ?: false
    }

    suspend fun saveUserGroup(group: String) {
        context.dataStore.edit {
            it[USER_GROUP] = group
        }
    }

    suspend fun saveUserAuth(isUserAuth: Boolean) {
        context.dataStore.edit {
            it[IS_USER_AUTH] = isUserAuth
        }
    }

    suspend fun clearData() {
        context.dataStore.edit {
            it.clear()
        }
    }

    companion object {
        val USER_GROUP = stringPreferencesKey("GROUP")
        val IS_USER_AUTH = booleanPreferencesKey("IS_USER_AUTH")
    }
}