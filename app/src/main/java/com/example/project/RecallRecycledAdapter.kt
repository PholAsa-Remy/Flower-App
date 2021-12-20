package com.example.project

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
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
            var seasonNow : Int = -1
            if (month > 0 && month < 13){
                if (month >=1 && month <= 3)
                    seasonNow = 0
                else if (month >=4 && month <= 6)
                    seasonNow = 1
                else if (month >=7 && month <= 9)
                    seasonNow = 2
                else if (month >=10 && month <= 12)
                    seasonNow = 3
            }
            var season = list.get(position).frequency.split(",")
            var period = Period.of(0, 0, season[seasonNow].toInt())
            var modifiedDate = date.plus(period)

            list.get(position).nextWatering = modifiedDate.toString()
            val t = Thread {
                model.dao.updateFlower(list.get(position))
                model.flowers.postValue(model.dao.loadNextWateringFlower(LocalDate.now().toString()).toList())
            }
            t.start()
            t.join()
        }
        holder.binding.cardViewRecall.setOnClickListener(updateNextWatering)
        try {
            holder.binding.flowerPicture.setImageBitmap(PhotoManager.loadPhoto(list.get(position).picture, recallContext))
        }catch(e : Exception){

        }

        if (list.get(position).nextNutriment == 0) {
            list.get(position).nextNutriment = list.get(position).nutrimentFrequency
            holder.binding.nutriment.setText("need nutriment!!!")
        }else{
            list.get(position).nextNutriment--
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

    class VH(val binding: ItemRecallLayoutBinding) : RecyclerView.ViewHolder( binding.root )
}