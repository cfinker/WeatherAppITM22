package at.finker.weatherappitm22.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val STORE_KEY_LOCATION = stringPreferencesKey("weather_location")

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
){
    val location: Flow<String> = dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[STORE_KEY_LOCATION] ?: "Kapfenberg"
        }

    suspend fun writeLocationToDataStore(location: String) {
        dataStore.edit { settings ->
            settings[STORE_KEY_LOCATION] = location
        }
    }

}
