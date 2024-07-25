package com.booktrails.core_module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.Preferences

class UserPreferenceManager(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val ONBOARDING_KEY = booleanPreferencesKey("onboarding_seen")
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
}