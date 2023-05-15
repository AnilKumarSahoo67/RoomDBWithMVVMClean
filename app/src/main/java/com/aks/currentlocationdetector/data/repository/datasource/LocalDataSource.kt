package com.aks.currentlocationdetector.data.repository.datasource

import androidx.lifecycle.LiveData
import com.aks.currentlocationdetector.ImageModel

interface LocalDataSource {

    suspend fun addImage(imageList: List<ImageModel>) : Int

    fun getAllImage() : LiveData<List<ImageModel>>

    suspend fun deleteImage(imageModel: ImageModel) : Int

    suspend fun deleteImageByImageId(imageId : Int) : Int

    suspend fun deleteAllImage() : Int


}