package com.example.project

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ItemRecallLayoutBinding
import java.lang.Exception
import java.time.LocalDate
import java.time.Period
import java.util.*

class RecallRecycledAdapter (private val model : FlowerViewModel, private val recallContext : RecallActivity) : RecyclerView.Adapter<RecallRecycledAdapter.VH>(){
    private var list : List<Flower> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemRecallLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    @SuppressLint("NewApi")
    private fun getModifiedDate (position : Int) : LocalDate {
        val calendar : Calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)
        val date = LocalDate.now()
        var seasonNow : Int = -1
        if (month in 1..12){
            when (month) {
                in 1..3 -> seasonNow = 0
                in 4..6 -> seasonNow = 1
                in 7..9 -> seasonNow = 2
                in 10..12 -> seasonNow = 3
            }
        }
        val season = list[position].frequency.split(",")
        val period = Period.of(0, 0, season[seasonNow].toInt())
        return date.plus(period)
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val updateNextWatering = View.OnClickListener {
            val modifiedDate = getModifiedDate (position)

            list[position].nextWatering = modifiedDate.toString()
            val t = Thread {
                model.dao.updateFlower(list[position])
                model.flowers.postValue(model.dao.loadNextWateringFlower(LocalDate.now().toString()).toList())
            }
            t.start()
            t.join()
        }
        holder.binding.cardViewRecall.setOnClickListener(updateNextWatering)
        try {
            holder.binding.flowerPicture.setImageBitmap(PhotoManager.loadPhoto(list[position].picture, recallContext))
        }catch(e : Exception){
            val flowerId = recallContext.resources.getIdentifier("flower", "drawable",recallContext.packageName)
            holder.binding.flowerPicture.setImageResource(flowerId)
        }

        if (list[position].nextNutriment == 0) {
            list[position].nextNutriment = list[position].nutrimentFrequency
            holder.binding.nutriment.text = "need nutriment!!!"
        }else{
            list[position].nextNutriment--
        }

        holder.binding.name.text = list[position].name
        holder.binding.latinName.text = list[position].latinName
        holder.binding.nextWatering.text = "Next Watering : ${list[position].nextWatering}"
    }

    override fun getItemCount(): Int = list.size

    fun majFlower (flower : List<Flower>){
        list = flower
        this.notifyDataSetChanged()
    }

    class VH(val binding: ItemRecallLayoutBinding) : RecyclerView.ViewHolder( binding.root )
}