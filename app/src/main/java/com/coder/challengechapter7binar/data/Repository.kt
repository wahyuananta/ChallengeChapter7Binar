package com.coder.challengechapter7binar.data

import com.coder.challengechapter7binar.data.api.ApiHelper
import com.coder.challengechapter7binar.data.datastore.UserDataStoreManager
import com.coder.challengechapter7binar.data.room.dao.UserDao
import com.coder.challengechapter7binar.data.room.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiHelper: ApiHelper,
    private val userDao: UserDao,
    private val userDataStore: UserDataStoreManager
    ) {

    //data store
    suspend fun saveToPref(user: UserEntity) {
        userDataStore.saveUserPref(user)
    }

    fun getUserPref(): Flow<UserEntity> {
        return userDataStore.getUserPref()
    }

    suspend fun deletePref() {
        userDataStore.deleteUserFromPref()
    }

    //room
    suspend fun register(user: UserEntity): Long {
        return userDao.addUser(user)
    }

    suspend fun login(username: String): UserEntity {
        return userDao.getUser(username)
    }

    suspend fun update(user: UserEntity): Int {
        return userDao.updateUser(user)
    }

    //api
    suspend fun getPopularMovies() = apiHelper.getPopularMovies()

    suspend fun getUpcomingMovies() = apiHelper.getUpcomingMovies()

    suspend fun getPopularMovies(movie_id: Int) = apiHelper.getMovieById(movie_id)
}