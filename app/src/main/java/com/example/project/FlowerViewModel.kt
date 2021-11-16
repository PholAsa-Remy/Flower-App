package com.example.project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/* This class is used for every call to the database */
class FlowerViewModel (application: Application) : AndroidViewModel(application) {
    val dao = FlowerBD.getDatabase(application).daoFlower()
    var flower = MutableLiveData<List<Flower>>()

    init{
        //Initialise flower with all the flower
        Thread { flower.postValue(dao.loadAll().toList()) }.start()
    }
}