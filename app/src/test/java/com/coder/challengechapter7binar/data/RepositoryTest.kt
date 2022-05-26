package com.coder.challengechapter7binar.data

import com.coder.challengechapter7binar.data.api.ApiHelper
import com.coder.challengechapter7binar.data.api.ApiService
import com.coder.challengechapter7binar.data.api.model.DetailMovieResponse
import com.coder.challengechapter7binar.data.api.model.PopularMovieResponse
import com.coder.challengechapter7binar.data.api.model.UpcomingMoviesResponse
import com.coder.challengechapter7binar.data.datastore.UserDataStoreManager
import com.coder.challengechapter7binar.data.room.DbHelper
import com.coder.challengechapter7binar.data.room.entity.UserEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class RepositoryTest {

    //collaborator
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var dbHelper: DbHelper
    private lateinit var userDataStore: UserDataStoreManager

    //system under test
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        apiService = mockk()
        dbHelper = mockk()
        userDataStore = mockk()
        apiHelper = ApiHelper(apiService)
        repository = Repository(apiHelper, dbHelper, userDataStore)
    }

    @Test
    fun getPopularMovies(): Unit = runBlocking {
        val responseMovies = mockk<PopularMovieResponse>()

        every {
            runBlocking {
                apiService.getPopularMovie()
            }
        } returns responseMovies

        repository.getPopularMovies()

        verify {
            runBlocking {
                apiService.getPopularMovie()
            }
        }
    }

    @Test
    fun getUpcomingMovies(): Unit = runBlocking {
        val responseMovies = mockk<UpcomingMoviesResponse>()

        every {
            runBlocking {
                apiService.getUpcomingMovie()
            }
        } returns responseMovies

        repository.getUpcomingMovies()

        verify {
            runBlocking {
                apiService.getUpcomingMovie()
            }
        }
    }

    @Test
    fun getMovieById(): Unit = runBlocking {
        val responseMovies = mockk<DetailMovieResponse>()

        every {
            runBlocking {
                apiService.getMovieById(12345)
            }
        } returns responseMovies

        repository.getMovieById(12345)

        verify {
            runBlocking {
                apiService.getMovieById(12345)
            }
        }
    }

    @Test
    fun saveUserPref(): Unit = runBlocking {
        val user = UserEntity(
            1,
            "test",
            "test",
            "test",
            "test"
        )

        every {
            runBlocking {
                userDataStore.saveUserPref(user)
            }
        } returns Unit

        repository.saveUserPref(user)
        verify {
            runBlocking {
                userDataStore.saveUserPref(user)
            }
        }
    }

    @Test
    fun getUserPref(): Unit = runBlocking {
        val responseGetUserPref = mockk<Flow<UserEntity>>()

        every {
            runBlocking {
                userDataStore.getUserPref()
            }
        } returns responseGetUserPref

        repository.getUserPref()
        verify {
            runBlocking {
                userDataStore.getUserPref()
            }
        }
    }

    @Test
    fun deleteUserFromPref(): Unit = runBlocking {
        every {
            runBlocking {
                userDataStore.deleteUserFromPref()
            }
        } returns Unit

        repository.deleteUserFromPref()
        verify {
            runBlocking {
                userDataStore.deleteUserFromPref()
            }
        }
    }

    @Test
    fun addUser(): Unit = runBlocking {
        val responseAddUser = 1L
        val user = UserEntity(
            1,
            "test",
            "test",
            "test",
            "test"
        )

        every {
            runBlocking {
                dbHelper.addUser(user)
            }
        } returns responseAddUser

        repository.addUser(user)
        verify {
            runBlocking {
                dbHelper.addUser(user)
            }
        }
    }

    @Test
    fun getUser(): Unit = runBlocking {
        val responseGetUser = mockk<UserEntity>()

        every {
            runBlocking {
                dbHelper.getUser("test")
            }
        } returns responseGetUser

        repository.getUser("test")
        verify {
            runBlocking {
                dbHelper.getUser("test")
            }
        }
    }

    @Test
    fun updateUser(): Unit = runBlocking {
        val responseUpdateUser = 1
        val user = UserEntity(
            1,
            "test",
            "test",
            "test",
            "test"
        )

        every {
            runBlocking {
                dbHelper.updateUser(user)
            }
        } returns responseUpdateUser

        repository.updateUser(user)
        verify {
            runBlocking {
                dbHelper.updateUser(user)
            }
        }
    }

}