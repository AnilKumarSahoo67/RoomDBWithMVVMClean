package com.aks.currentlocationdetector.data.repository.repositoryImpl

import androidx.lifecycle.LiveData
import com.aks.currentlocationdetector.ImageModel
import com.aks.currentlocationdetector.data.repository.NetworkResources
import com.aks.currentlocationdetector.data.repository.datasource.LocalDataSource
import com.aks.currentlocationdetector.domain.repository.Repository

class RepositoryImpl(private var localDataSource: LocalDataSource) : Repository {

    override suspend fun addImage(imageList: List<ImageModel>): NetworkResources<Int> {
        return NetworkResources.Success(localDataSource.addImage(imageList))
    }

    override fun getAllImage(): NetworkResources<LiveData<List<ImageModel>>> {
        return NetworkResources.Success(localDataSource.getAllImage())
    }


    override suspend fun deleteImage(imageModel: ImageModel): NetworkResources<Int> {
        return NetworkResources.Success(localDataSource.deleteImage(imageModel))
    }

    override suspend fun deleteImageByImageId(imageId: Int): NetworkResources<Int> {
        return NetworkResources.Success(localDataSource.deleteImageByImageId(imageId))
    }

    override suspend fun deleteAllImage(): NetworkResources<Int> {
        return NetworkResources.Success(localDataSource.deleteAllImage())
    }
}