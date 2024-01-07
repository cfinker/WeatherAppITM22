package at.finker.weatherappitm22.ui

import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import at.finker.weatherappitm22.WeatherApplication
import at.finker.weatherappitm22.data.UserPreferencesRepository
import at.finker.weatherappitm22.data.WeatherData
import at.finker.weatherappitm22.network.WeatherApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WeatherViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {


    var weatherUiState: WeatherUiState by mutableStateOf(WeatherUiState.InputRequired)
        private set

    var location by mutableStateOf("")

    fun getWeatherData() {
        viewModelScope.launch {
            userPreferencesRepository.writeLocationToDataStore(location)
        }

        val appid = "4d3801ce61114b8ab8d124306230605"; // not the best way to place the api key in a app, but for now it will be okay
        viewModelScope.launch {
            weatherUiState = WeatherUiState.Loading
            weatherUiState = try {
                if(location.isEmpty()) {
                    WeatherUiState.Error
                } else {
                    WeatherUiState.Success(WeatherApi.retrofitService.getWeatherData(location, appid))
                }
            } catch (e: IOException) {
                Log.e("weather http io", e.toString())
                WeatherUiState.Error
            } catch (e: HttpException) {
                Log.e("weather http", e.toString())
                WeatherUiState.Error
            }
        }
    }

    fun getWeatherDataForCurrentLocation(currentLocation: Location) {
        location = currentLocation.latitude.toString() + ',' + currentLocation.longitude.toString();
        getWeatherData();
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WeatherApplication)
                WeatherViewModel(application.userPreferencesRepository)
            }
        }
    }

    fun loadLocationFromDataStorage() {
        viewModelScope.launch {
            userPreferencesRepository.location.collect { value ->
                location = value
            }
        }
    }


}