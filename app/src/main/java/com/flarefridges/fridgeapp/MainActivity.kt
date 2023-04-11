package com.flarefridges.fridgeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.flarefridges.fridgeapp.navigation.AppNavHost
import com.flarefridges.fridgeapp.ui.theme.FridgeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FridgeAppTheme {
                AppNavHost()
            }
        }
    }
}