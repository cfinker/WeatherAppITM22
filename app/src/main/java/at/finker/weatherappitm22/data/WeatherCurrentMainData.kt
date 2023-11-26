package at.finker.weatherappitm22.data

import kotlinx.serialization.Serializable

@Serializable
data class WeatherCurrentMainData(
    val text: String,
)

