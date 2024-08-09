package com.booktrails.core_module.service

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class UserPreferenceManager(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val ONBOARDING_KEY = booleanPreferencesKey("onboarding_seen")
        private val KEY_USER_ID = stringPreferencesKey("user_id")
    }

    // Save the onboarding status (seen or not)
    suspend fun saveOnboardingStatus(seen: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_KEY] = seen
        }
    }

    // Check if the onboarding screen has been seen
    suspend fun isOnboardingSeen(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[ONBOARDING_KEY] ?: false
    }

    suspend fun setUserId(id: String) {
        dataStore.edit {
            it[KEY_USER_ID] = id
        }
    }

    suspend fun getUserId(): String? {
        return dataStore.data.map {
            it[KEY_USER_ID]
        }.firstOrNull()
    }


}
