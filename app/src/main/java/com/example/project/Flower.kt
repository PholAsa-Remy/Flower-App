package com.example.project

import androidx.room.*
import androidx.room.PrimaryKey




@Entity
data class Flower(
    var name : String,
    var latinName : String,
    var frequency: String,
    var nutrimentFrequency : Int,
    var picture : String)

{
    @PrimaryKey(autoGenerate = true) var id : Int = 0
    var nextWatering: String = ""
    var nextNutriment : Int = nutrimentFrequency
}
