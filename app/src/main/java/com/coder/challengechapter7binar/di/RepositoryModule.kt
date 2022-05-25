package com.coder.challengechapter7binar.di

import com.coder.challengechapter7binar.data.Repository
import com.coder.challengechapter7binar.data.api.ApiHelper
import com.coder.challengechapter7binar.data.datastore.UserDataStoreManager
import com.coder.challengechapter7binar.data.room.DbHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @ViewModelScoped
    @Provides
    fun provideRepository(
        apiHelper: ApiHelper,
        dbHelper: DbHelper,
        userDataStore: UserDataStoreManager
    ) = Repository(apiHelper,dbHelper,userDataStore)
}