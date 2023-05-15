package com.aks.currentlocationdetector

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class ImageAdapter : RecyclerView.Adapter<ImageAdapter.MyImageViewHolder>() {


    var differ = AsyncListDiffer(this,object : DiffUtil.ItemCallback<ImageModel>(){

        override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
            return oldItem == newItem
        }

    })

    private var callBack : ((ImageModel, Int)->Unit) ?= null

    fun onClickListener(onPressed : ((ImageModel,Int)->Unit)){
        this.callBack = onPressed
    }

    inner class MyImageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private var imageView : ImageView = itemView.findViewById(R.id.imageView)
        fun bind(imageModel: ImageModel, position: Int) {
            imageView.setImageURI(Uri.parse(imageModel.imageUrl))

            itemView.setOnClickListener {
                callBack?.invoke(imageModel,position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyImageViewHolder {
        return MyImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_layout,parent,false))
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyImageViewHolder, position: Int) {
        holder.bind(differ.currentList[position],position)
    }
}