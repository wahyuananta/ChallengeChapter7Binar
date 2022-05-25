package com.coder.challengechapter7binar.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coder.challengechapter5binar.api.model.DetailMovieResponse
import com.coder.challengechapter5binar.api.service.ApiClient
import com.coder.challengechapter7binar.data.api.model.DetailMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val detailMovie: MutableLiveData<DetailMovieResponse> = MutableLiveData()

    fun getMovieById(id: Int) {
        ApiClient.getInstance(getApplication()).getMovieById(id).enqueue(object : Callback<DetailMovieResponse> {
            override fun onResponse(
                call: Call<DetailMovieResponse>,
                response: Response<DetailMovieResponse>
            ) {
                detailMovie.postValue(response.body())
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                Log.d("DetailViewModel", "${t.message}")
            }

        })
    }
}