package com.example.project

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// The Database of the application
@Database(entities = [Flower::class], version = 6)
abstract class FlowerBD : RoomDatabase() {
    abstract fun daoFlower(): DaoFlower

    companion object {
        @Volatile
        private var instance: FlowerBD? = null
        fun getDatabase(context : Context): FlowerBD {

            if (instance != null)
                return instance!!
            val db = Room.databaseBuilder(
                context.applicationContext,
                FlowerBD::class.java, "flower"
            ).build()
            instance = db
            return instance!!
        }
    }
}