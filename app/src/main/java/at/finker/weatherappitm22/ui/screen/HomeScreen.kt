package at.finker.weatherappitm22.ui.screen

import android.Manifest
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.finker.weatherappitm22.R
import at.finker.weatherappitm22.data.WeatherData
import at.finker.weatherappitm22.ui.WeatherUiState
import at.finker.weatherappitm22.ui.WeatherViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel = viewModel(),
    weatherUiState: WeatherUiState,
    locationPermissionRequest: ActivityResultLauncher<Array<String>>?,
    modifier: Modifier = Modifier
) {
    Log.d("weatherUiState switch", weatherUiState.toString())
    when (weatherUiState) {
        //  is WeatherUiState.Loading -> LoadingScreen(modifier)
        is WeatherUiState.Success -> ResultScreen(weatherUiState.weatherData, modifier)
        /// is WeatherUiState.Error -> ErrorScreen(modifier)
        is WeatherUiState.InputRequired -> InputScreen(navController, weatherViewModel, locationPermissionRequest)
        else -> InputScreen(navController, weatherViewModel, locationPermissionRequest)
    }
}

@Composable
fun ResultScreen(weatherData: WeatherData, modifier: Modifier = Modifier
    .fillMaxSize().wrapContentSize(Alignment.Center)) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = weatherData.current.temp_c.toString())
        Text(text = weatherData.current.condition.text)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(navController: NavController, weatherViewModel: WeatherViewModel,
                locationPermissionRequest: ActivityResultLauncher<Array<String>>?,
                modifier: Modifier = Modifier
    .fillMaxSize()
    .wrapContentSize(Alignment.Center)) {

    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello ${weatherViewModel.location}"
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.location)) },
            placeholder = { Text(text = stringResource(id = R.string.locationPlaceholder)) },
            leadingIcon = {
                Icon(
                    Icons.Rounded.LocationOn,
                    contentDescription = stringResource(id = R.string.icon_location)
                )
            },
            value = weatherViewModel.location,
            onValueChange = {
                weatherViewModel.location = it
            }
        )

        Button(onClick = {
            weatherViewModel.getWeatherData()
        }) {
            Text(stringResource(R.string.search))
        }

        Button(onClick = {
            locationPermissionRequest?.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
        }) {
            Text(stringResource(R.string.currentLocation))
        }

        Button(onClick = {
            navController.navigate("imprint")
        }) {
            Text("Imprint")
        }

    }
}