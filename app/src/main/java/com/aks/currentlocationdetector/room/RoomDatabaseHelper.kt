package com.aks.currentlocationdetector.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aks.currentlocationdetector.ImageModel

@Database(entities = [ImageModel::class], version = 2, exportSchema = true)
abstract class RoomDatabaseHelper : RoomDatabase() {

    abstract fun imageSelectorDao(): ImageSelectorDao

    companion object {
        @Volatile
        private var instance: RoomDatabaseHelper? = null
        fun getInstance(context: Context): RoomDatabaseHelper {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, RoomDatabaseHelper::class.java, "ImageDB")
                            .fallbackToDestructiveMigration()
                            .build()

                    return instance !!
                }
                return instance!!
            }
        }
    }

}