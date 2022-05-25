package com.coder.challengechapter7binar.data.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getPopularMovies() = apiService.getPopularMovie()

    suspend fun getUpcomingMovies() = apiService.getUpcomingMovie()

    suspend fun getMovieById(movie_id: Int) = apiService.getMovieById(movie_id)
}