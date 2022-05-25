package com.coder.challengechapter7binar.data

import com.coder.challengechapter7binar.data.api.ApiHelper
import com.coder.challengechapter7binar.data.datastore.UserDataStoreManager
import com.coder.challengechapter7binar.data.room.DbHelper
import com.coder.challengechapter7binar.data.room.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiHelper: ApiHelper,
    private val dbHelper: DbHelper,
    private val userDataStore: UserDataStoreManager
    ) {

    // Api
    suspend fun getPopularMovies() = apiHelper.getPopularMovies()

    suspend fun getUpcomingMovies() = apiHelper.getUpcomingMovies()

    suspend fun getMovieById(movie_id: Int) = apiHelper.getMovieById(movie_id)

    // DataStore
    suspend fun saveUserPref(user: UserEntity) {
        userDataStore.saveUserPref(user)
    }

    fun getUserPref(): Flow<UserEntity> {
        return userDataStore.getUserPref()
    }

    suspend fun deleteUserFromPref() {
        userDataStore.deleteUserFromPref()
    }

    // Room
    suspend fun addUser(user: UserEntity): Long = dbHelper.addUser(user)

    suspend fun getUser(username: String): UserEntity {
        return dbHelper.getUser(username)
    }

    suspend fun updateUser(user: UserEntity): Int = dbHelper.updateUser(user)

}