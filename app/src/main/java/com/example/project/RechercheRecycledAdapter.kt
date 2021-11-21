package com.example.project

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ItemRechercheLayoutBinding

//TODO : Remplacer list avec autre chose
class RechercheRecycledAdapter (val model : FlowerViewModel, val launcher: ActivityResultLauncher<Intent>,val rechercheContext : RechercheActivity) : RecyclerView.Adapter<RechercheRecycledAdapter.VH>(){
    var list : List<Flower> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RechercheRecycledAdapter.VH {
        val binding = ItemRechercheLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)

        return RechercheRecycledAdapter.VH(binding)
    }

    override fun onBindViewHolder(holder: RechercheRecycledAdapter.VH, position: Int) {
        // On Click : Modify the flower with the primary key of the flower
        val modifyListener = View.OnClickListener { view ->
            val goToModify : Intent = Intent (rechercheContext, ModifyActivity:: class.java)
            goToModify.putExtra("name",list.get(position).name)
            launcher.launch(goToModify)
        }
        holder.binding.cardViewRecherche.setOnClickListener(modifyListener)

        holder.binding.name.setText(list.get(position).name)
    }

    override fun getItemCount(): Int = list.size

    fun maj_flower (flower : List<Flower>){
        list = flower
        this.notifyDataSetChanged()
    }

    class VH(val binding: ItemRechercheLayoutBinding) : RecyclerView.ViewHolder( binding.root )

}