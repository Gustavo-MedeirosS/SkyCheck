package com.example.skycheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skycheck.presentation.route.CurrentLocation
import com.example.skycheck.presentation.route.Locations
import com.example.skycheck.presentation.route.Onboarding
import com.example.skycheck.presentation.screen.current_location.CurrentLocationScreen
import com.example.skycheck.presentation.screen.locations.LocationsScreen
import com.example.skycheck.presentation.screen.onboarding.OnboardingScreen
import com.example.skycheck.presentation.theme.SkyCheckTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkyCheckTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Onboarding) {
                    composable<Onboarding> {
                        OnboardingScreen(navController = navController)
                    }
                    composable<Locations> {
                        LocationsScreen(navController = navController)
                    }
                    composable<CurrentLocation> {
                        CurrentLocationScreen(navController = navController)
                    }
                }
            }
        }
    }
}