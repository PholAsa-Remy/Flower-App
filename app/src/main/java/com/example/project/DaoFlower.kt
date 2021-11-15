package com.example.project

import androidx.room.*

@Dao
interface DaoFlower {

    @Insert(entity = Flower::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertPays(vararg flower: Flower) : List<Long>

    @Query("SELECT * FROM Flower")
    fun loadAll() : Array<Flower>

    @Update
    fun updateFlower(flower : Flower)
}