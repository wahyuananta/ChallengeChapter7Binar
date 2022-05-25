package com.coder.challengechapter7binar.ui.detail

import androidx.lifecycle.*
import com.coder.challengechapter7binar.data.Repository
import com.coder.challengechapter7binar.data.api.Resource
import com.coder.challengechapter7binar.data.api.model.DetailMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
//    val detailMovie: MutableLiveData<DetailMovieResponse> = MutableLiveData()

    private val _detailMovie: MutableLiveData<Resource<DetailMovieResponse>> = MutableLiveData()
    val detailMovie: LiveData<Resource<DetailMovieResponse>> get() = _detailMovie

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            _detailMovie.postValue(Resource.loading())
            try {
                _detailMovie.postValue(Resource.success(repository.getMovieById(id)))
            }catch (exception:Exception){
                _detailMovie.postValue(Resource.error(exception.localizedMessage?:"Error occured"))
            }
        }
//        ApiClient.getInstance(getApplication()).getMovieById(id).enqueue(object : Callback<DetailMovieResponse> {
//            override fun onResponse(
//                call: Call<DetailMovieResponse>,
//                response: Response<DetailMovieResponse>
//            ) {
//                detailMovie.postValue(response.body())
//            }
//
//            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
//                Log.d("DetailViewModel", "${t.message}")
//            }
//
//        })
    }
}