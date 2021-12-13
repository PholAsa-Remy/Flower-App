package com.example.project

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ItemRecallLayoutBinding
import java.lang.Exception
import java.time.LocalDate
import java.time.Period
import java.util.*

//TODO : Remplacer list avec autre chose
class RecallRecycledAdapter (val model : FlowerViewModel, val recallContext : RecallActivity) : RecyclerView.Adapter<RecallRecycledAdapter.VH>(){
    var list : List<Flower> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecallRecycledAdapter.VH {
        val binding = ItemRecallLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val updateNextWatering = View.OnClickListener { view ->
            var calendar : Calendar = Calendar.getInstance()
            var month = calendar.get(Calendar.MONTH)
            var date = LocalDate.now()
            var seasonNow : Int = month/4
            var season = list.get(position).frequency.split(",")
            var period = Period.of(0, 0, season[seasonNow].toInt())
            var modifiedDate = date.plus(period)

            list.get(position).nextWatering = modifiedDate.toString()
            model.updateFlower(list.get(position))

            model.loadNextWateringFlower(LocalDate.now().toString())
            this.notifyDataSetChanged()

        }
        holder.binding.cardViewRecall.setOnClickListener(updateNextWatering)
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