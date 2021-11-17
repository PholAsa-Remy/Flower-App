package com.example.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ItemRechercheLayoutBinding

//TODO : Remplacer list avec autre chose
class RechercheRecycledAdapter () : RecyclerView.Adapter<RechercheRecycledAdapter.VH>(){
    var list : List<Flower> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RechercheRecycledAdapter.VH {
        val binding = ItemRechercheLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return RechercheRecycledAdapter.VH(binding)
    }

    override fun onBindViewHolder(holder: RechercheRecycledAdapter.VH, position: Int) {
        holder.binding.name.setText(list.get(position).name)
    }

    override fun getItemCount(): Int = list.size

    fun maj_flower (flower : List<Flower>){
        list = flower
        this.notifyDataSetChanged()
    }

    class VH(val binding: ItemRechercheLayoutBinding) : RecyclerView.ViewHolder( binding.root )

}