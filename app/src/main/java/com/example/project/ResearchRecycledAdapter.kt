package com.example.project

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ItemResearchLayoutBinding
import java.lang.Exception

class ResearchRecycledAdapter (private val launcher: ActivityResultLauncher<Intent>, private val researchContext : ResearchActivity) : RecyclerView.Adapter<ResearchRecycledAdapter.VH>(){
    // List of flower shown by the recycled view
    private var list : List<Flower> = mutableListOf()

    // Create View Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemResearchLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    // For each card/Flower
    override fun onBindViewHolder(holder: VH, position: Int) {
        // On Click : Modify the flower with the primary key of the flower
        val modifyListener = View.OnClickListener {
            val goToModify = Intent (researchContext, ModifyActivity:: class.java)
            goToModify.putExtra("id", list[position].id)
            launcher.launch(goToModify)
        }
        holder.binding.cardViewResearch.setOnClickListener(modifyListener)

        // Flower Picture
        try {
            holder.binding.flowerPicture.setImageBitmap(PhotoManager.loadPhoto(list[position].picture, researchContext))
        }catch(e : Exception){
            val flowerId = researchContext.resources.getIdentifier("flower", "drawable",researchContext.packageName)
            holder.binding.flowerPicture.setImageResource(flowerId)
        }

        // Flower Information
        holder.binding.name.text = list[position].name
        holder.binding.latinName.text = list[position].latinName
        holder.binding.nextWatering.text = "Next Watering : ${list[position].nextWatering}"
    }

    override fun getItemCount(): Int = list.size

    // Update list
    fun majFlower (flower : List<Flower>){
        list = flower
        this.notifyDataSetChanged()
    }

    class VH(val binding: ItemResearchLayoutBinding) : RecyclerView.ViewHolder( binding.root )

}