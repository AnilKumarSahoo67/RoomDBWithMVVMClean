package com.aks.currentlocationdetector.di.datasource

import com.aks.currentlocationdetector.data.repository.datasource.LocalDataSource
import com.aks.currentlocationdetector.data.repository.datasourceImpl.LocalDataSourceImpl
import com.aks.currentlocationdetector.room.RoomDatabaseHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Provides
    @Singleton

    fun provideDataSourceModule(roomDatabaseHelper: RoomDatabaseHelper) : LocalDataSource{
        return LocalDataSourceImpl(roomDatabaseHelper)
    }
}