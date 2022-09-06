package com.example.asteroidRadarApp.dataBase

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.asteroidRadarApp.model.AsteroidModel
import com.example.asteroidRadarApp.model.PictureOfDayModel
import com.example.asteroidRadarApp.retrofit.getNextSevenDaysFormattedDates
import com.example.asteroidRadarApp.until.Constants
import java.text.SimpleDateFormat
import java.util.*

fun getToday(): String {
    return SimpleDateFormat(
        Constants.API_QUERY_DATE_FORMAT,
        Locale.getDefault()
    ).format(Calendar.getInstance().time)
}

fun getWeek(): List<String> {
    return getNextSevenDaysFormattedDates().toList()
}

@Dao
interface DataBaseDAO {
    /** Asteroid */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: AsteroidModel)

    @Query("DELETE FROM Asteroid_table WHERE closeApproachDate < :Today")
    suspend fun clearAsteroids(Today: String = getToday())
    //filter

    @Query("SELECT * FROM Asteroid_table ORDER BY closeApproachDate")
    fun getAsteroidAllList(): LiveData<List<AsteroidModel>>

    @Query("SELECT * FROM Asteroid_table WHERE closeApproachDate = :today")
    fun getAsteroidTodayList(today: String = getToday()): LiveData<List<AsteroidModel>>

    @Query("SELECT * FROM Asteroid_table WHERE closeApproachDate IN (:WeekList) ORDER BY closeApproachDate")
    fun getAsteroidWeekList(WeekList: List<String> = getWeek()):LiveData<List<AsteroidModel>>

    /** Picture */
    @Insert
    suspend fun insert(item: PictureOfDayModel)

    @Query("SELECT * FROM Picture_table")
    fun getPicture(): LiveData<PictureOfDayModel>

    @Query("DELETE FROM Picture_table")
    suspend fun clearPicture()

}

@Database(
    entities = [AsteroidModel::class, PictureOfDayModel::class],
    version = 1,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    abstract val dao: DataBaseDAO
}

@Volatile
private lateinit var dp: DataBase

fun getDatabaseDAOInstance(context: Context): DataBaseDAO {
    synchronized(DataBase::class.java) {
        if (!::dp.isInitialized) {
            dp = Room.databaseBuilder(
                context.applicationContext,
                DataBase::class.java,
                "AsteroidDatabase"
            ).fallbackToDestructiveMigration().build()
        }
        return dp.dao
    }
}
