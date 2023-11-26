package at.finker.weatherappitm22.network

import at.finker.weatherappitm22.data.WeatherData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.weatherapi.com/v1/"
private val json = Json { ignoreUnknownKeys = true }

/**
 * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        json.asConverterFactory(
            MediaType.get("application/json")))
    .baseUrl(BASE_URL)
    .build()

/**
 * Retrofit service object for creating api calls
 */
interface WeatherApiService {
    @GET("current.json")
    suspend fun getWeatherData(@Query("q") location: String, @Query("key") key: String): WeatherData
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}
