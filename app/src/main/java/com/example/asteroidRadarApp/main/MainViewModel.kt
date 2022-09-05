package com.example.asteroidRadarApp.main

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.asteroidRadarApp.dataBase.getAsteroidDatabaseDAOInstance
import com.example.asteroidRadarApp.dataBase.getPictureDatabaseDAOInstance
import com.example.asteroidRadarApp.model.AsteroidModel
import com.example.asteroidRadarApp.model.PictureOfDayModel
import com.example.asteroidRadarApp.model.State
import com.example.asteroidRadarApp.retrofit.getAsteroidAPIInstance
import com.example.asteroidRadarApp.retrofit.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.IllegalArgumentException

class MainViewModel(private val context: Context) : ViewModel() {

    private val AsteroidDP = getAsteroidDatabaseDAOInstance(context)
    private val PictureDP = getPictureDatabaseDAOInstance(context)

    private var _data = AsteroidDP.getAsteroidList()
    val data: LiveData<List<AsteroidModel>>
        get() = _data

    private val _today: LiveData<PictureOfDayModel> = PictureDP.getPicture()
    val today: LiveData<PictureOfDayModel>
        get() = _today

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    init {
        _state.value = State.Loading
        getToday()
        getAllDay()
    }

    private fun getAllDay() {
        viewModelScope.launch {
            try {
                val result = getAsteroidAPIInstance().getAllDayAsteroid()
                val ObjectResult = parseAsteroidsJsonResult(JSONObject(result))
                AsteroidDP.clear()
                AsteroidDP.insert(*ObjectResult.toTypedArray())
                _state.value = State.Done
            } catch (e: Exception) {
                _state.value = State.Done
                Log.i("asd",e.message.toString())
            }
        }
    }

    private fun getToday() {
        viewModelScope.launch {
            try {
                val pictureOfDayModel = getAsteroidAPIInstance().getTodayAsteroid()
                PictureDP.clear()
                PictureDP.insert(pictureOfDayModel)
                _state.value = State.Done
            } catch (e: Exception) {
                _state.value = State.Done
                Toast.makeText(context, "Check Your Network Connection", Toast.LENGTH_SHORT).show()
            }
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

