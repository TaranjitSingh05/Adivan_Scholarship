package com.example.adivan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.adivan.navigation.AdivanNavHost
import com.example.adivan.ui.theme.AdivanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdivanTheme {
                AdivanNavHost()
            }
        }
    }
}
