package com.example.coroutines

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.request.RequestListener
import kotlinx.coroutines.*
import java.lang.Exception

class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
   val imageView=itemView.findViewById<ImageView>(R.id.item_image)
   lateinit var scope:CoroutineScope
   fun setProccess(context:MainActivity,url:String){
      Glide.with(context).load(url).centerCrop().into(imageView)
   }
   suspend  fun setImage(string:String): Bitmap {

         val input =java.net.URL(string).openStream();
      val image= BitmapFactory.decodeStream(input)
      return image

   }
}
