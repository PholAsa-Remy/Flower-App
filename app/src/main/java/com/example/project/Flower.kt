package com.example.project

import androidx.room.*
import androidx.room.PrimaryKey




@Entity
data class Flower(
    var name : String,
    var latinName : String,
    var frequency: String,
    var nutrimentFrequency : Int )

{
    @PrimaryKey(autoGenerate = true) var id : Int = 0
    var picture : String = "$id.jpg"
    var nextWatering: String = ""
    var nextNutriment : Int = nutrimentFrequency
}
