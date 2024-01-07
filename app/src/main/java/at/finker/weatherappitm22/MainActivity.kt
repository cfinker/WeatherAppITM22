package at.finker.weatherappitm22

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.finker.weatherappitm22.ui.WeatherViewModel
import at.finker.weatherappitm22.ui.screen.HomeScreen
import at.finker.weatherappitm22.ui.theme.WeatherAppITM22Theme
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(applicationContext,"No location permission", Toast.LENGTH_SHORT).show();
                    }
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location : Location? ->
                            // Got last known location. In some rare situations this can be null.
                            Log.d("geolocation location obj", location.toString())
                            if(location != null) {
                                weatherViewModel.getWeatherDataForCurrentLocation(location)
                            } else {
                                val fallbackLocation = Location("") //provider name is unnecessary
                                fallbackLocation.latitude = 47.5018
                                fallbackLocation.longitude = 15.4347
                                weatherViewModel.getWeatherDataForCurrentLocation(fallbackLocation)
                            }
                        }
                } else -> {
                    Toast.makeText(applicationContext,"No location permission", Toast.LENGTH_SHORT).show();
                }
            }
        }

        setContent {
            val navController = rememberNavController()
            weatherViewModel = viewModel(
                factory = WeatherViewModel.Factory
            )
            weatherViewModel.loadLocationFromDataStorage()

            WeatherAppITM22Theme {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            navController = navController,
                            weatherViewModel = weatherViewModel,
                            weatherUiState = weatherViewModel.weatherUiState,
                            locationPermissionRequest = locationPermissionRequest
                        )
                    }
                    composable("imprint") {
                        ImprintScreen(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun ImprintScreen(navController: NavController,
                  modifier: Modifier = Modifier
                      .fillMaxSize()
                      .wrapContentSize(Alignment.Center)) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "developed by Christian Finker")
    }

    Button(onClick = {
        navController.navigate("home")
    }) {
        Text("go back")
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppITM22Theme {
        //HomeScreen(navController = NavController())
    }
}