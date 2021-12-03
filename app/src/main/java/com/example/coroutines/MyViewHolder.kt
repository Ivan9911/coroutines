package com.example.coroutines

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import java.lang.Exception

class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
   val imageView=itemView.findViewById<ImageView>(R.id.item_image)
   lateinit var scope:CoroutineScope
   fun setProccess(context:MainActivity,url:String){
      scope= CoroutineScope(Dispatchers.IO)
      scope.launch {
         val image=async{setImage(url)}.await()
         context.runOnUiThread{

               Glide.with(context).load(image).centerCrop().into(imageView)


         }
      }
   }
   suspend  fun setImage(string:String): Bitmap {

         val input =java.net.URL(string).openStream();
      val image= BitmapFactory.decodeStream(input)
      return image

   }
}
