package com.example.project

import androidx.room.*

@Entity
data class Flower(@PrimaryKey val name : String, val picture : String, val period : String, val nextWatering: String,val frequency:Int)
