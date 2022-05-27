package com.coder.challengechapter7binar.data.room.dao

import androidx.room.*
import com.coder.challengechapter7binar.data.room.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT EXISTS(SELECT * FROM UserEntity WHERE username = :username and password = :password)")
    suspend fun checkUser(username: String, password: String): Boolean

    @Query("SELECT * FROM UserEntity WHERE username like :username")
    suspend fun getUser(username: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userEntity: UserEntity): Long

    @Update
    suspend fun updateUser(userEntity: UserEntity): Int
}