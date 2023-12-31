package com.example.project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/* This class is used for every call to the database */
class FlowerViewModel (application: Application) : AndroidViewModel(application) {
    // The database of all Flower
    val dao = FlowerBD.getDatabase(application).daoFlower()
    //List of flower after loading
    var flowers = MutableLiveData<List<Flower>>()
    val insertInfo = MutableLiveData(-1)

    fun loadAllFlower (){
        Thread {
            flowers.postValue(dao.loadAll().toList())
        }.start()
    }

    fun insertFlower(vararg flower : Flower) {
        Thread {
            val l = dao.insertFlower( *flower )
            insertInfo.postValue ( l.fold(0)
            { acc: Int, n: Long  -> if(n >= 0) acc +1 else acc })
        }.start()
    }

    fun updateFlower (flower : Flower) {
        Thread{
            dao.updateFlower(flower)
        }.start()
    }

    fun loadFlower (id : Int){
        Thread {
            flowers.postValue(dao.loadFlower(id).toList())
        }.start()
    }

    fun loadPartialFlower (name : String){
        Thread {
            flowers.postValue(dao.loadPartialFlower(name).toList())
        }.start()
    }

    fun loadNextWateringFlower (date : String){
        Thread {
            flowers.postValue(dao.loadNextWateringFlower(date).toList())
        }.start()
    }

    fun deleteFlower (flower:Flower){
        Thread {
            dao.deleteFlower(flower)
        }.start()
    }
}