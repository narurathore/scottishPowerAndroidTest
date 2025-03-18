package com.narayan.singh.scottishpowerandroidtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.narayan.singh.scottishpowerandroidtest.presentation.navigation.NavGraph
import com.narayan.singh.scottishpowerandroidtest.ui.theme.ScottishPowerAndroidTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScottishPowerAndroidTestTheme {
                setContent {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }
    }
}