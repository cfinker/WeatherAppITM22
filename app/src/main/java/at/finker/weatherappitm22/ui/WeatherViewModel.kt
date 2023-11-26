package at.finker.weatherappitm22.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.finker.weatherappitm22.data.WeatherData
import at.finker.weatherappitm22.network.WeatherApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class WeatherViewModel: ViewModel() {

    var weatherUiState: WeatherUiState by mutableStateOf(WeatherUiState.InputRequired)
        private set

    var location by mutableStateOf("")

    fun getWeatherData() {
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
                WeatherUiState.Error
            } catch (e: HttpException) {
                WeatherUiState.Error
            }
        }
    }
}