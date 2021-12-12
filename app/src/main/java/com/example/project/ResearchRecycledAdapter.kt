package com.example.project

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ItemResearchLayoutBinding
import java.io.File
import java.io.FileInputStream
import java.lang.Exception

//TODO : Remplacer list avec autre chose
class ResearchRecycledAdapter (val model : FlowerViewModel, val launcher: ActivityResultLauncher<Intent>, val researchContext : ResearchActivity) : RecyclerView.Adapter<ResearchRecycledAdapter.VH>(){
    var list : List<Flower> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResearchRecycledAdapter.VH {
        val binding = ItemResearchLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)

        return ResearchRecycledAdapter.VH(binding)
    }

    override fun onBindViewHolder(holder: ResearchRecycledAdapter.VH, position: Int) {
        // On Click : Modify the flower with the primary key of the flower
        val modifyListener = View.OnClickListener { view ->
            val goToModify : Intent = Intent (researchContext, ModifyActivity:: class.java)
            goToModify.putExtra("id",list.get(position).id)
            launcher.launch(goToModify)
        }
        holder.binding.cardViewRecherche.setOnClickListener(modifyListener)
        //TODO : à tester
        try {
            holder.binding.flowerPicture.setImageBitmap(PhotoManager.loadPhoto(list.get(position).picture, researchContext))
        }catch(e : Exception){

        }
        holder.binding.name.setText(list.get(position).name)
        holder.binding.latinName.setText(list.get(position).latinName)
        holder.binding.nextWatering.setText("Next Watering : ${list.get(position).nextWatering}")
    }

    override fun getItemCount(): Int = list.size

    fun maj_flower (flower : List<Flower>){
        list = flower
        this.notifyDataSetChanged()
    }

    class VH(val binding: ItemResearchLayoutBinding) : RecyclerView.ViewHolder( binding.root )

}