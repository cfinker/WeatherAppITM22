package at.finker.weatherappitm22

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import at.finker.weatherappitm22.ui.WeatherViewModel
import at.finker.weatherappitm22.ui.screen.HomeScreen
import at.finker.weatherappitm22.ui.theme.WeatherAppITM22Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val weatherViewModel: WeatherViewModel = viewModel(
                factory = WeatherViewModel.Factory
            )
            weatherViewModel.loadLocationFromDataStorage()

            WeatherAppITM22Theme {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            navController = navController,
                            weatherViewModel = weatherViewModel,
                            weatherUiState = weatherViewModel.weatherUiState
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