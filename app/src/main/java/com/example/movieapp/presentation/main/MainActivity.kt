package com.example.movieapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapp.presentation.ui.navigation.navigationBottom.BottomNavigationScreen
import com.example.movieapp.presentation.ui.navigation.popularMovie.details.compose.DetailsPopularScreen
import com.example.movieapp.presentation.ui.navigation.popularMovie.movieList.compose.PopularMovieListScreen
import com.example.movieapp.presentation.ui.theme.MovieAppTheme
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