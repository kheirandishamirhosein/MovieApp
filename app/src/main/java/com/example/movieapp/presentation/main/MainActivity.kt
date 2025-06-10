package com.example.movieapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.movieapp.presentation.navigation.BottomNavigationScreen
import com.example.movieapp.util.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize MovieViewModel using Factory
        //val factory = Factory(DIContainer.provideMovieRepository())
        //movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        setContent {
            MovieAppTheme {
                BottomNavigationScreen()
            }
        }
    }

}