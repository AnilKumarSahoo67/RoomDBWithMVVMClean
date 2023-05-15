package com.aks.currentlocationdetector.data.repository.datasourceImpl

import androidx.lifecycle.LiveData
import com.aks.currentlocationdetector.ImageModel
import com.aks.currentlocationdetector.data.repository.datasource.LocalDataSource
import com.aks.currentlocationdetector.room.RoomDatabaseHelper

class LocalDataSourceImpl(private val roomDatabaseHelper: RoomDatabaseHelper) : LocalDataSource {

    override suspend fun addImage(imageList: List<ImageModel>): Int {
        roomDatabaseHelper.imageSelectorDao().addImage(imageList)
        return 0
    }

    override fun getAllImage(): LiveData<List<ImageModel>> {
        return roomDatabaseHelper.imageSelectorDao().getAllImage()
    }

    override suspend fun deleteImage(imageModel: ImageModel): Int {
        roomDatabaseHelper.imageSelectorDao().deleteImage(imageModel)

        return 0
    }

    override suspend fun deleteImageByImageId(imageId: Int): Int {
        roomDatabaseHelper.imageSelectorDao().deleteImageByImageId(imageId)

        return 0
    }

    override suspend fun deleteAllImage(): Int {
        roomDatabaseHelper.imageSelectorDao().deleteAllImage()

        return 0
    }
}