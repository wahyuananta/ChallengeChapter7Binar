package com.coder.challengechapter7binar.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coder.challengechapter5binar.api.model.PopularMovieResponse
import com.coder.challengechapter5binar.api.model.UpcomingMoviesResponse
import com.coder.challengechapter5binar.api.service.ApiClient
import com.coder.challengechapter7binar.data.api.model.PopularMovieResponse
import com.coder.challengechapter7binar.data.api.model.UpcomingMoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application): AndroidViewModel(application) {
    val upcomingMovie: MutableLiveData<UpcomingMoviesResponse> = MutableLiveData()
    val popularMovie: MutableLiveData<PopularMovieResponse> = MutableLiveData()

    fun getUpcomingMovie() {
        ApiClient.getInstance(getApplication()).getUpcomingMovie().enqueue(object : Callback<UpcomingMoviesResponse> {
            override fun onResponse(
                call: Call<UpcomingMoviesResponse>,
                response: Response<UpcomingMoviesResponse>
            ) {
                response.body()?.let {
                    upcomingMovie.postValue(it)
                }
            }

            override fun onFailure(call: Call<UpcomingMoviesResponse>, t: Throwable) {
                Log.d("UpcomingViewModel", "${t.message}")
            }

        })
    }

    fun getPopularMovie() {
        ApiClient.getInstance(getApplication()).getPopularMovie().enqueue(object : Callback<PopularMovieResponse> {
            override fun onResponse(
                call: Call<PopularMovieResponse>,
                response: Response<PopularMovieResponse>
            ) {
            response.body()?.let {
                popularMovie.postValue(it)
                }
            }

            override fun onFailure(call: Call<PopularMovieResponse>, t: Throwable) {
                Log.d("PopularViewModel", "${t.message}")
            }

        })
    }
}