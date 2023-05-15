package com.aks.currentlocationdetector.di.core

import android.app.Application
import com.aks.currentlocationdetector.room.RoomDatabaseHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {


    @Provides
    @Singleton
    fun provideDbModule(application: Application) : RoomDatabaseHelper{
        return RoomDatabaseHelper.getInstance(application)
    }


}