package com.example.coroutines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

class Adapter(lista:MutableList<String>) : RecyclerView.Adapter<MyViewHolder>(){
    val lista= lista
    val scopes= mutableListOf<CoroutineScope>()
    lateinit var context:MainActivity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder  {
        context=parent.context as MainActivity
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_item_image,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setProccess(context,lista.get(position))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)


    }
    override fun getItemCount(): Int {
        return lista.size-2
    }




}