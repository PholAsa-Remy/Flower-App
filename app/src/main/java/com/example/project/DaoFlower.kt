package com.example.project

import androidx.room.*

@Dao
interface DaoFlower {

    @Insert(entity = Flower::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertFlower(vararg flower: Flower) : List<Long>

    @Query("SELECT * FROM Flower")
    fun loadAll() : Array<Flower>

    @Query("SELECT * FROM Flower WHERE name = :name")
    fun loadFlower(name : String) : Array<Flower>

    @Query("SELECT * FROM Flower f WHERE f.name LIKE :name || '%'")
    fun loadPartialFlower(name : String) : Array<Flower>

    @Update
    fun updateFlower(flower : Flower)

    @Delete
    fun deleteFlower(flower : Flower) : Int
}