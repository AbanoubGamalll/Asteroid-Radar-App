package com.example.asteroidRadarApp.main

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.asteroidRadarApp.dataBase.getAsteroidDatabaseDAOInstance
import com.example.asteroidRadarApp.model.AsteroidModel
import com.example.asteroidRadarApp.model.PictureOfDayModel
import com.example.asteroidRadarApp.model.State
import com.example.asteroidRadarApp.retrofit.getAsteroidAPIInstance
import com.example.asteroidRadarApp.retrofit.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.IllegalArgumentException

class MainViewModel(private val context: Context) : ViewModel() {

    private val dp = getAsteroidDatabaseDAOInstance(context)

    private var _data: LiveData<List<AsteroidModel>> = dp.getAsteroidList()
    val data: LiveData<List<AsteroidModel>>
        get() = _data

    private val _today = MutableLiveData<PictureOfDayModel>()
    val today: LiveData<PictureOfDayModel>
        get() = _today

    init {
         getToday()
        getAllDay()
    }

    private  fun getAllDay() {
        viewModelScope.launch {
            try {
                val result = getAsteroidAPIInstance().getAllDayAsteroid()
                val ObjectResult: ArrayList<AsteroidModel> = parseAsteroidsJsonResult(JSONObject(result))
                dp.clear()
                dp.insert(*ObjectResult.toTypedArray())
                Log.i("asd","OK " + ObjectResult.size  +"   "+ ObjectResult.toString())
            } catch (e: Exception) {
                Log.i("asd", "Error " + e.message.toString())
            }
        }
    }

    private fun getToday() {
        viewModelScope.launch {
            _today.value = PictureOfDayModel()
            try {
                val pictureOfDayModel = getAsteroidAPIInstance().getTodayAsteroid()
                pictureOfDayModel.state = State.Done
                _today.value = pictureOfDayModel
            } catch (e: Exception) {
                Toast.makeText(context, "Check Your Network Connection", Toast.LENGTH_SHORT).show()
                _today.value = PictureOfDayModel(state = State.Error, title = "")
            }
        }
        today.value?.state = State.Finish
    }

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

