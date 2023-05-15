package com.aks.currentlocationdetector.di.viewmodel

import com.aks.currentlocationdetector.domain.repository.Repository
import com.aks.currentlocationdetector.viewmodel.MainViewModel
import com.aks.currentlocationdetector.viewmodel.MainViewModelProviderFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Provides
    @Singleton
    fun provideViewModelProvider(repository: Repository) : MainViewModelProviderFactory{
        return MainViewModelProviderFactory(repository)
    }
}