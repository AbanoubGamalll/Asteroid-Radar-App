package com.example.asteroidRadarApp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asteroidRadarApp.model.AsteroidModel

class MainViewModel : ViewModel() {
    private val _data = MutableLiveData<List<AsteroidModel>>()
    val data: LiveData<List<AsteroidModel>>
        get() = _data


    fun add(){

        val list = mutableListOf<AsteroidModel>()
        list.add(AsteroidModel(1,"a","a",1.0,1.0,1.0,1.0,true))
        list.add(AsteroidModel(2,"b","a",1.0,1.0,1.0,1.0,true))
        list.add(AsteroidModel(3,"c","a",1.0,1.0,1.0,1.0,false))
        list.add(AsteroidModel(4,"d","a",1.0,1.0,1.0,1.0,true))
        list.add(AsteroidModel(5,"e","a",1.0,1.0,1.0,1.0,true))

        _data.value = list

    }

}