package com.coder.challengechapter7binar.ui.home

import androidx.lifecycle.*
import com.coder.challengechapter7binar.data.Repository
import com.coder.challengechapter7binar.data.api.Resource
import com.coder.challengechapter7binar.data.api.model.PopularMovieResponse
import com.coder.challengechapter7binar.data.api.model.UpcomingMoviesResponse
import com.coder.challengechapter7binar.data.room.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
//    val upcomingMovie: MutableLiveData<UpcomingMoviesResponse> = MutableLiveData()
//    val popularMovie: MutableLiveData<PopularMovieResponse> = MutableLiveData()

    private val  _resultRegister : MutableLiveData<Long> = MutableLiveData()
    val resultRegister : LiveData<Long> get() = _resultRegister

    private val _resultUpdate :  MutableLiveData<Int> = MutableLiveData()
    val resultUpdate :LiveData<Int> get() = _resultUpdate

    private val  _resultLogin : MutableLiveData<UserEntity> = MutableLiveData()
    val resultLogin : LiveData<UserEntity> get() = _resultLogin

    private val _user: MutableLiveData<UserEntity> = MutableLiveData()
    val user: LiveData<UserEntity> get() = _user

    private val _popularMovie: MutableLiveData<Resource<PopularMovieResponse>> = MutableLiveData()
    val popularMovie: LiveData<Resource<PopularMovieResponse>> get() = _popularMovie

    private val _upcomingMovie: MutableLiveData<Resource<UpcomingMoviesResponse>> = MutableLiveData()
    val upcomingMovie: LiveData<Resource<UpcomingMoviesResponse>> get() = _upcomingMovie

    fun addUser(user: UserEntity){
        viewModelScope.launch {
            _resultRegister.value = repository.addUser(user)
        }
    }

    fun getDataStore() {
        viewModelScope.launch {
            repository.getUserPref().collect {
                _user.value = it
            }
        }
    }

    fun deleteUserFromPref() {
        viewModelScope.launch {
            repository.deleteUserFromPref()
        }
    }

    fun saveDataStore(user: UserEntity) {
        viewModelScope.launch {
            repository.saveUserPref(user)
        }
    }

    fun getUser(username: String) {
        viewModelScope.launch {
            _resultLogin.value = repository.getUser(username)
        }
    }

    fun updateUser(user:UserEntity){
        viewModelScope.launch {
            _resultUpdate.value = repository.updateUser(user)
        }
    }

    fun getUpcomingMovie() {
        viewModelScope.launch {
            _upcomingMovie.postValue(Resource.loading())
            try {
                _upcomingMovie.postValue(Resource.success(repository.getUpcomingMovies()))
            }catch (exception:Exception){
                _upcomingMovie.postValue(Resource.error(exception.localizedMessage?:"Error occured"))
            }
        }
//        ApiClient.getInstance(getApplication()).getUpcomingMovie().enqueue(object : Callback<UpcomingMoviesResponse> {
//            override fun onResponse(
//                call: Call<UpcomingMoviesResponse>,
//                response: Response<UpcomingMoviesResponse>
//            ) {
//                response.body()?.let {
//                    upcomingMovie.postValue(it)
//                }
//            }
//
//            override fun onFailure(call: Call<UpcomingMoviesResponse>, t: Throwable) {
//                Log.d("UpcomingViewModel", "${t.message}")
//            }
//
//        })
    }

    fun getPopularMovie() {
        viewModelScope.launch {
            _popularMovie.postValue(Resource.loading())
            try {
                _popularMovie.postValue(Resource.success(repository.getPopularMovies()))
            }catch (exception:Exception){
                _popularMovie.postValue(Resource.error(exception.localizedMessage?:"Error occured"))
            }
        }
//        ApiClient.getInstance(getApplication()).getPopularMovie().enqueue(object : Callback<PopularMovieResponse> {
//            override fun onResponse(
//                call: Call<PopularMovieResponse>,
//                response: Response<PopularMovieResponse>
//            ) {
//            response.body()?.let {
//                popularMovie.postValue(it)
//                }
//            }
//
//            override fun onFailure(call: Call<PopularMovieResponse>, t: Throwable) {
//                Log.d("PopularViewModel", "${t.message}")
//            }
//
//        })
    }
}