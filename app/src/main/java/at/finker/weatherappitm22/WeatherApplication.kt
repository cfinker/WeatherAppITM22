package at.finker.weatherappitm22

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import at.finker.weatherappitm22.data.UserPreferencesRepository

private const val WEATHER_PREFERENCE_NAME = "weather_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = WEATHER_PREFERENCE_NAME
)

class WeatherApplication: Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}