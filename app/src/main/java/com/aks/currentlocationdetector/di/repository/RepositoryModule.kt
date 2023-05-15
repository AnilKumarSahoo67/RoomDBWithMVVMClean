package com.aks.currentlocationdetector.di.repository

import com.aks.currentlocationdetector.data.repository.datasource.LocalDataSource
import com.aks.currentlocationdetector.data.repository.repositoryImpl.RepositoryImpl
import com.aks.currentlocationdetector.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepositoryModule(localDataSource: LocalDataSource) : Repository{
        return RepositoryImpl(localDataSource)
    }
}