package com.example.movieapp

import app.cash.turbine.test
import com.example.movieapp.data.remote.api.ApiService
import com.example.movieapp.data.remote.model.movie.MovieResponse
import com.example.movieapp.data.remote.model.movie.ResultMovie
import com.example.movieapp.data.repository.Repository
import com.example.movieapp.presentation.movie.details.MovieDetailViewModel
import com.example.movieapp.presentation.movie.list.MovieViewModel
import com.example.movieapp.presentation.state.ResultStates
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class RepositoryTest {

    // Mock ApiService
    private val apiService: ApiService = mockk()

    // Repository instance
    private val repository = Repository(apiService)

    @Test
    fun `getPopularMovies emits Success when api call is successful`() = runTest {
        // Mock the getPopularMovies method to return a successful response
        val movieResponse = MovieResponse(
            page = 1,
            results = listOf(
                ResultMovie(
                    adult = false,
                    backdropPath = "/path/to/backdrop",
                    genreIds = listOf(1, 2),
                    id = 1,
                    originalLanguage = "en",
                    originalTitle = "Original Title 1",
                    overview = "Overview 1",
                    popularity = 123.45,
                    posterPath = "/path/to/poster",
                    releaseDate = "2024-01-01",
                    title = "Movie 1",
                    video = false,
                    voteAverage = 7.8,
                    voteCount = 100
                ),
                ResultMovie(
                    adult = false,
                    backdropPath = "/path/to/backdrop2",
                    genreIds = listOf(3, 4),
                    id = 2,
                    originalLanguage = "es",
                    originalTitle = "Original Title 2",
                    overview = "Overview 2",
                    popularity = 67.89,
                    posterPath = "/path/to/poster2",
                    releaseDate = "2024-02-01",
                    title = "Movie 2",
                    video = false,
                    voteAverage = 8.1,
                    voteCount = 150
                )
            ),
            totalPages = 10,
            totalResults = 20
        )
        coEvery { apiService.getPopularMovies() } returns movieResponse

        // Use Turbine to test the Flow
        repository.getPopularMovies().test {
            // Assert that Success is emitted with the expected data
            assertEquals(
                ResultStates.Success(movieResponse),
                awaitItem()
            )

            // Ensure that the Flow completes successfully
            awaitComplete()
        }
    }


    @Test
    fun `getPopularMovies emits Error when api call fails`() = runTest {
        // Mock the getPopularMovies method to throw an exception
        val exception = Exception("Mocked Exception")
        coEvery { apiService.getPopularMovies() } throws exception

        // Use Turbine to test the Flow
        repository.getPopularMovies().test {
            // Assert that Error is emitted with the expected exception
            assertEquals(
                ResultStates.Error(exception),
                awaitItem()
            )

            // Ensure that the Flow completes successfully
            awaitComplete()
        }
    }

}

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: Repository
    private lateinit var viewModel: MovieViewModel
    private val testScope = TestCoroutineScope(dispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)  // Set the main dispatcher to our test dispatcher
        repository = mockk()
        viewModel = MovieViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()  // Reset the dispatcher to the original state
    }

    @Test
    fun `fetchPopularMovies should emit Loading, Success when repository returns data`(): Unit =
        testScope.runTest {
            // Arrange
            val mockMovieResponse = MovieResponse(
                page = 1,
                results = listOf(
                    ResultMovie(
                        adult = false,
                        backdropPath = "/path/to/backdrop.jpg",
                        genreIds = listOf(1, 2, 3),
                        id = 1,
                        originalLanguage = "en",
                        originalTitle = "Movie Title",
                        overview = "Movie Overview",
                        popularity = 10.0,
                        posterPath = "/path/to/poster.jpg",
                        releaseDate = "2024-08-08",
                        title = "Movie Title",
                        video = false,
                        voteAverage = 8.0,
                        voteCount = 100
                    )
                ),
                totalPages = 1,
                totalResults = 1
            )

            coEvery { repository.getPopularMovies() } returns flow {
                emit(ResultStates.Loading)
                emit(ResultStates.Success(mockMovieResponse))
            }

            // Act & Assert
            viewModel.movies.test {
                assertEquals(ResultStates.Loading, awaitItem())
                assertEquals(ResultStates.Success(mockMovieResponse), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `fetchPopularMovies should emit Loading, Error when repository returns an error`(): Unit =
        testScope.runTest {
            // Arrange
            val exception = Exception("Network error")
            coEvery { repository.getPopularMovies() } returns flow {
                emit(ResultStates.Loading)
                emit(ResultStates.Error(exception))
            }

            // Act & Assert
            viewModel.movies.test {
                assertEquals(ResultStates.Loading, awaitItem())
                assertTrue(awaitItem() is ResultStates.Error)
                cancelAndIgnoreRemainingEvents()
            }
        }

}

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: Repository
    private lateinit var viewModel: MovieDetailViewModel
    private val testScope = TestCoroutineScope(dispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)  // Set the main dispatcher to our test dispatcher
        repository = mockk()
        viewModel = MovieDetailViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()  // Reset the dispatcher to the original state
    }

    @Test
    fun `fetchMovieDetails should emit Loading and Success state when repository returns data`() =
        testScope.runTest {
            // Arrange
            val movieId = 1
            val mockResultMovie = ResultMovie(
                adult = false,
                backdropPath = "/path/to/backdrop.jpg",
                genreIds = listOf(1, 2, 3),
                id = movieId,
                originalLanguage = "en",
                originalTitle = "Movie Title",
                overview = "Movie Overview",
                popularity = 10.0,
                posterPath = "/path/to/poster.jpg",
                releaseDate = "2024-08-08",
                title = "Movie Title",
                video = false,
                voteAverage = 8.0,
                voteCount = 100
            )

            coEvery { repository.getMovieDetails(movieId) } returns flowOf(
                ResultStates.Success(mockResultMovie)
            )

            // Act
            viewModel.fetchMovieDetails(movieId)

            // Assert
            viewModel.movieDetail.test {
                // Assert Loading state
                assertEquals(ResultStates.Loading, awaitItem())
                // Assert Success state with expected data
                assertEquals(ResultStates.Success(mockResultMovie), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `fetchMovieDetails should emit Loading and Error state when repository returns an error`() =
        testScope.runTest {
            // Arrange
            val movieId = 1
            val exception = Exception("Network error")
            coEvery { repository.getMovieDetails(movieId) } returns flowOf(
                ResultStates.Error(exception)
            )

            // Act
            viewModel.fetchMovieDetails(movieId)

            // Assert
            viewModel.movieDetail.test {
                // Assert Loading state
                assertEquals(ResultStates.Loading, awaitItem())
                // Assert Error state with expected exception
                assertTrue(awaitItem() is ResultStates.Error)
                cancelAndIgnoreRemainingEvents()
            }
        }


}