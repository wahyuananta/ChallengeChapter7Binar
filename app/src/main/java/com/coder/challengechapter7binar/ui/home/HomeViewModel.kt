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

    private val  _getDataUser : MutableLiveData<UserEntity> = MutableLiveData()
    val getDataUser : LiveData<UserEntity> get() = _getDataUser

    private val _userDataStore: MutableLiveData<UserEntity> = MutableLiveData()
    val userDataStore: LiveData<UserEntity> get() = _userDataStore

    private val _popularMovie: MutableLiveData<Resource<PopularMovieResponse>> = MutableLiveData()
    val popularMovie: LiveData<Resource<PopularMovieResponse>> get() = _popularMovie

    private val _upcomingMovie: MutableLiveData<Resource<UpcomingMoviesResponse>> = MutableLiveData()
    val upcomingMovie: LiveData<Resource<UpcomingMoviesResponse>> get() = _upcomingMovie

    fun getDataStore() {
        viewModelScope.launch {
            repository.getUserPref().collect {
                _userDataStore.value = it
            }
        }
    }

    fun getUser(username: String) {
        viewModelScope.launch {
            _getDataUser.value = repository.getUser(username)
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
    }
}