package com.aks.currentlocationdetector

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "image_table")
data class ImageModel(
    @ColumnInfo(name = "image_url")
    val imageUrl : String?=null,
    @ColumnInfo(name = "image_name")
    var imageName : String ?= null,
    @ColumnInfo(name = "image_id")
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
)
