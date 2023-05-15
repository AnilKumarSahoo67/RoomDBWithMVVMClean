package com.aks.currentlocationdetector.domain.repository

import androidx.lifecycle.LiveData
import com.aks.currentlocationdetector.ImageModel
import com.aks.currentlocationdetector.data.repository.NetworkResources
import com.aks.currentlocationdetector.room.RoomDatabaseHelper

interface Repository {

    suspend fun addImage(imageList: List<ImageModel>) : NetworkResources<Int>

    fun getAllImage() : NetworkResources<LiveData<List<ImageModel>>>

    suspend fun deleteImage(imageModel: ImageModel) : NetworkResources<Int>

    suspend fun deleteImageByImageId(imageId : Int) : NetworkResources<Int>

    suspend fun deleteAllImage() : NetworkResources<Int>
}