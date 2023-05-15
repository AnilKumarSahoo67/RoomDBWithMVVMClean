package com.aks.currentlocationdetector

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aks.currentlocationdetector.data.repository.NetworkResources
import com.aks.currentlocationdetector.databinding.ActivityMainBinding
import com.aks.currentlocationdetector.viewmodel.MainViewModel
import com.aks.currentlocationdetector.viewmodel.MainViewModelProviderFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var name : String?= null
    var newCompanyObj : Company ?= null
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var binding : ActivityMainBinding
    private var imageList = arrayListOf<ImageModel>()
    private val id = 0

    @Inject
    lateinit var mainViewModelProviderFactory : MainViewModelProviderFactory

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        viewModel = ViewModelProvider(this,mainViewModelProviderFactory)[MainViewModel::class.java]

        Log.e("Anil", "Late init property initialized : ${::imageAdapter.isInitialized}")
        imageAdapter = ImageAdapter()
        Log.e("Anil", "Late init property initialized : ${::imageAdapter.isInitialized}")
        binding.recyclerView.adapter = imageAdapter

        //scope function example
        //as we know kotlin has 5 scope function
        // 1. let
        // 2. with
        // 3. run
        // 4. apply
        // 5. also

        /**1. let */

        //## The context object is available as an argument (it)
        //## The Return value is lambda
        // Example:-
         val length = name?.let {
             it.length
         }
        Log.e("Anil", "Length of string : $length" )

        name?.let {
            println(it)
        }

        name = "Anil"

        name?.let {
            println(name)
        }

        //If we are giving valid string then it will return the length of the string
        //If we are giving a null value to string name then the length will return as null
        //As you see when the value of ‘name’ is ‘null’ let function simply avoid the code block.
        // Hence, solving the biggest nightmare of programmers – NullPointerException.

        /**2. with */

        //## The context object is available as a receiver (this)
        //## The return value is a lambda result

        val company = Company().apply {
            name = "Anil"
            empCode = "ISU379"
            designation = "Junior Software Engineer"
        }

        with(company){
            Log.e("Anil", "Employee Details: ${company.name} ${company.empCode} ${company.designation}")
        }

        //With this object do the following

        /**3. run */

        //## The context object used as a receiver (this)
        // ## Return a lambda

        newCompanyObj = Company().apply {
            name = "Pujarani"
            empCode = "ISU342"
            designation = "SDE II"
        }
        newCompanyObj?.run {
            Log.e("Anil", "Employee Details: $name $empCode $designation")
        }

        /** 4. apply */

        //## The context object is used as a receiver (this)
        //## The return value is the object itself

        //Example :-

        val companyObj = Company().apply {
            name = "Amit"
            empCode = "ISU382"
            designation = "Junior Software Engineer"
        }
        Log.e("Anil", "Employee Details: ${companyObj.name} ${companyObj.empCode} ${companyObj.designation}")


        /**5. also */
        //## The context object is available as an argument (it)
        //## The return is a object itself

        //Example
        val obj = Company().apply {
            name = "Anil"
        }
        obj.also {
            it.empCode = "ISU379"
            it.designation = "SDE II"
        }

        Log.e("Anil", "Employee Details: ${obj.name} ${obj.empCode} ${obj.designation}")

        binding.fabFullBtn.setOnClickListener {
            chooseImageFromGalleryLauncher.launch("image/*")
        }

        binding.chatFab.setOnClickListener {
            chooseImageFromGalleryLauncher.launch("image/*")
        }


        binding.recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY){
                binding.chatFabText.visibility = View.GONE
            }else if (scrollX == scrollY){
                binding.chatFabText.visibility = View.VISIBLE
            }
            else{
                binding.chatFabText.visibility = View.VISIBLE
            }
        }


        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getAllImage()
        }
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getAllImageResource.observe(this@MainActivity){
                when(it){
                    is NetworkResources.Loading->{
                        binding.loader.visibility = View.VISIBLE
                    }
                    is NetworkResources.Success->{
                        binding.loader.visibility = View.GONE
                        it.data?.let {list->
                           observeLiveData(list)
                        }
                    }
                    is NetworkResources.Error->{
                        binding.loader.visibility = View.GONE
                        Toast.makeText(this@MainActivity," Error ${it.message}",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewModel.addImageResource.observe(this@MainActivity){
            when(it){
                is NetworkResources.Loading ->{
                    binding.loader.visibility = View.VISIBLE
                }

                is NetworkResources.Success->{
                    binding.loader.visibility = View.GONE
                    Toast.makeText(this@MainActivity,"Image Added Successfully",Toast.LENGTH_SHORT).show()
                }

                is NetworkResources.Error->{
                    binding.loader.visibility = View.GONE
                    Toast.makeText(this@MainActivity," Error ${it.message}",Toast.LENGTH_SHORT).show()
                    Log.e("Anil", "onCreate: ${it.message}", )
                }
            }
        }



    }

    private fun observeLiveData(list: LiveData<List<ImageModel>>) {
        list.observe(this@MainActivity){
            if (it.isNotEmpty()){
                imageAdapter.differ.submitList(it)
                imageAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this@MainActivity,"No Image Found",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val chooseImageFromGalleryLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()){
        imageList = arrayListOf()
        it?.let {
            for (uri in it){
                val uri = convertUriToBmp(uri)
                imageList.add(ImageModel(uri,"Anil"))
            }

            if (imageList.isNotEmpty()){
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.addImages(imageList)
                }
            }
        }
    }

    private fun convertUriToBmp(uri: Uri): String {
        val bmp = MediaStore.Images.Media.getBitmap(contentResolver,uri)
        return getImageUriFromBitMap(bmp)
    }

    private fun getImageUriFromBitMap(bmp: Bitmap?): String {
        val bytes = ByteArrayOutputStream()
        bmp?.compress(Bitmap.CompressFormat.JPEG,100,bytes)
        val path = MediaStore.Images.Media.insertImage(contentResolver,bmp,"File",null)
        return path.toString()
    }

    inner class Company{
        var name : String ?= null
        var empCode : String ?= null
        var designation : String ?= null
    }
}