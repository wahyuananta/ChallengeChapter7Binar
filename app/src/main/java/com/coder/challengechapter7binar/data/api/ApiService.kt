package com.coder.challengechapter7binar.data.api

import com.coder.challengechapter7binar.data.api.model.DetailMovieResponse
import com.coder.challengechapter7binar.data.api.model.PopularMovieResponse
import com.coder.challengechapter7binar.data.api.model.UpcomingMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/upcoming?api_key=d75df6abb480d067ab096c09ffbf1082")
    suspend fun getUpcomingMovie(): UpcomingMoviesResponse

    @GET("movie/popular?api_key=d75df6abb480d067ab096c09ffbf1082")
    suspend fun getPopularMovie(): PopularMovieResponse

    @GET("movie/{movie_id}?api_key=d75df6abb480d067ab096c09ffbf1082")
    suspend fun getMovieById(@Path("movie_id") movie_id: Int): DetailMovieResponse
}