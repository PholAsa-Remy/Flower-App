package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ItemRecallLayoutBinding

//TODO : Remplacer list avec autre chose
class RecallRecycledAdapter () : RecyclerView.Adapter<RecallRecycledAdapter.VH>(){
    var list : List<Flower> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecallRecycledAdapter.VH {
        val binding = ItemRecallLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.name.setText(list.get(position).name)
    }

    override fun getItemCount(): Int = list.size

    fun maj_flower (flower : List<Flower>){
        list = flower
        this.notifyDataSetChanged()
    }

    class VH(val binding: ItemRecallLayoutBinding) : RecyclerView.ViewHolder( binding.root )
}