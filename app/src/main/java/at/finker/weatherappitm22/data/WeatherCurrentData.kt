package at.finker.weatherappitm22.data

import kotlinx.serialization.Serializable

@Serializable
data class WeatherCurrentData(
    val temp_c: Float,
    val humidity: Float,
    val wind_kph: Float,
    val condition: WeatherCurrentMainData,
)
