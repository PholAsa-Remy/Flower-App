package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//TODO : Remplacer list avec autre chose
class RecallRecycledAdapter (var list : List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recall_layout, parent, false)

        val holder = VH(view)

        /* TODO : Changer ici pour selectionner ce qu'on clique et ajouter un bouton arroser
        var listener = View.OnClickListener { view ->
            val etu : Etudiant = sortedList.get(holder.absoluteAdapterPosition)
            etu.checked = !etu.checked
            if (etu.checked){
                holder.itemView.findViewById<CardView>(R.id.cardView).setCardBackgroundColor(colorChecked)
            }else{
                setpaircolor (holder, holder.absoluteAdapterPosition)
            }
            if (sortColumn.equals("checked")){
                sortedList.updateItemAt( holder.absoluteAdapterPosition, etu)
            }
        }
        view.setOnClickListener(listener)*/

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.name).setText(list.get(position))
    }

    override fun getItemCount(): Int = list.size

    class VH(view: View) : RecyclerView.ViewHolder(view)
}