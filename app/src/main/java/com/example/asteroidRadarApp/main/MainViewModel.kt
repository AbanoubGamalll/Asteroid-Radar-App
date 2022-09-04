package com.example.asteroidRadarApp.main

import android.content.Context
import androidx.lifecycle.*
import com.example.asteroidRadarApp.dataBase.getAsteroidDatabaseDAOInstance
import com.example.asteroidRadarApp.model.AsteroidModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(context: Context) : ViewModel() {

    private val dp = getAsteroidDatabaseDAOInstance(context)

    private var _data: LiveData<List<AsteroidModel>> = dp.getAsteroidList()
    val data: LiveData<List<AsteroidModel>>
        get() = _data

    fun insert(vararg model: AsteroidModel) {
        viewModelScope.launch {
            dp.insert(*model)
        }
    }

    fun clear() {
        viewModelScope.launch {
            dp.clear()
        }
    }
}


class MainViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

