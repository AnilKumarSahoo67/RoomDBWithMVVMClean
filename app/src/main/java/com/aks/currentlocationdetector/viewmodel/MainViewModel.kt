package com.aks.currentlocationdetector.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aks.currentlocationdetector.ImageModel
import com.aks.currentlocationdetector.data.repository.NetworkResources
import com.aks.currentlocationdetector.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(
    private val repository: Repository
) : ViewModel() {

    val addImageResource: MutableLiveData<NetworkResources<Int>> = MutableLiveData()
    val getAllImageResource : MutableLiveData<NetworkResources<LiveData<List<ImageModel>>>> = MutableLiveData()
    val deleteImageResource : MutableLiveData<NetworkResources<Int>> = MutableLiveData()
    val deleteImageByIdResource : MutableLiveData<NetworkResources<Int>> = MutableLiveData()
    val deleteAllImageResource : MutableLiveData<NetworkResources<Int>> = MutableLiveData()


    fun addImages(imageList: List<ImageModel>){
        viewModelScope.launch (Dispatchers.IO){
            try {
                addImageResource.postValue(NetworkResources.Loading())
                addImageResource.postValue(repository.addImage(imageList))
            }catch (e : Exception){
                addImageResource.postValue(NetworkResources.Error(e.message))
            }
        }
    }


    fun getAllImage(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getAllImageResource.postValue(NetworkResources.Loading())
                getAllImageResource.postValue(repository.getAllImage())
            }catch (e : Exception){
                getAllImageResource.postValue(NetworkResources.Error(e.message))
            }
        }
    }
}