package com.example.project

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ItemRechercheLayoutBinding
import java.io.File
import java.io.FileInputStream
import java.lang.Exception

//TODO : Remplacer list avec autre chose
class ResearchRecycledAdapter (val model : FlowerViewModel, val launcher: ActivityResultLauncher<Intent>, val researchContext : ResearchActivity, val fileDirectory : File) : RecyclerView.Adapter<ResearchRecycledAdapter.VH>(){
    var list : List<Flower> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResearchRecycledAdapter.VH {
        val binding = ItemRechercheLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)

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
            holder.binding.flowerPicture.setImageBitmap(loadPhoto(list.get(position).picture))
        }catch(e : Exception){

        }
        holder.binding.name.setText(list.get(position).name)
    }

    override fun getItemCount(): Int = list.size

    fun maj_flower (flower : List<Flower>){
        list = flower
        this.notifyDataSetChanged()
    }

    private fun loadPhoto (filename : String) : Bitmap {
        var f = File(fileDirectory, filename)
        var b : Bitmap = BitmapFactory.decodeStream(FileInputStream(f))
        return b
    }

    class VH(val binding: ItemRechercheLayoutBinding) : RecyclerView.ViewHolder( binding.root )

}