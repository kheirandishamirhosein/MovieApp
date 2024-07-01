package com.example.movieapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.di.DIContainer
import com.example.movieapp.di.Factory
import com.example.movieapp.presentation.viewmodel.MovieViewModel
import com.example.movieapp.ui.home.HomeScreen
import com.example.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize MovieViewModel using Factory
        val factory = Factory(DIContainer.provideMovieRepository())
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        setContent {
            MovieAppTheme {
                HomeScreen(movieViewModel)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieAppTheme {

    }
}