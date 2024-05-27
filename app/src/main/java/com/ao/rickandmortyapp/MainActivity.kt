package com.ao.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ao.rickandmortyapp.ui.theme.RickAndMortyAppTheme
import com.ao.rickandmortyapp.views.home.HomePage
import com.ao.rickandmortyapp.views.home.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomePage(
                        homeViewModel = HomeViewModel()
                    )
                }
            }
        }
    }
}