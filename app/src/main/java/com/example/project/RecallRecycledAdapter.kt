package com.example.project

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ItemRecallLayoutBinding
import java.lang.Exception

//TODO : Remplacer list avec autre chose
class RecallRecycledAdapter (val model : FlowerViewModel, val recallContext : RecallActivity) : RecyclerView.Adapter<RecallRecycledAdapter.VH>(){
    var list : List<Flower> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecallRecycledAdapter.VH {
        val binding = ItemRecallLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        try {
            holder.binding.flowerPicture.setImageBitmap(PhotoManager.loadPhoto(list.get(position).picture, recallContext))
        }catch(e : Exception){

        }
        holder.binding.name.setText(list.get(position).name)
    }

    override fun getItemCount(): Int = list.size

    fun maj_flower (flower : List<Flower>){
        list = flower
        this.notifyDataSetChanged()
    }

    class VH(val binding: ItemRecallLayoutBinding) : RecyclerView.ViewHolder( binding.root )
}