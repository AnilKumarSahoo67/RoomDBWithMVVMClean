package com.aks.currentlocationdetector.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aks.currentlocationdetector.ImageModel

@Dao
interface ImageSelectorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImage(imageList : List<ImageModel>)

    @Query("SELECT * FROM image_table")
    fun getAllImage() : LiveData<List<ImageModel>>
    // why not use suspend ? because Room does not support LiveData with suspended functions.
    // LiveData already works on a background thread and should be used directly without using coroutines

    @Delete
    suspend fun deleteImage(imageModel: ImageModel)

    @Query("DELETE FROM image_table WHERE image_id = :id") //you can use this too, for delete note by id.
    suspend fun deleteImageByImageId(id: Int)


    @Query("DELETE FROM image_table")
    suspend fun deleteAllImage()
}