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
import com.example.movieapp.presentation.ui.popular.details.compose.DetailsPopularScreen
import com.example.movieapp.presentation.ui.popular.movieList.compose.PopularMovieListScreen
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
                NavGraph()
            }
        }
    }
}

@Composable
fun NavGraph(startDestination: String = "movieList") {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable("movieList") {
            PopularMovieListScreen(navController)
        }
        composable(
            "movieDetail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailsPopularScreen(navController, movieId)
        }
    }
}