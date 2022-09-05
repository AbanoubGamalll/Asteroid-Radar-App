package com.example.asteroidRadarApp.dataBase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.asteroidRadarApp.model.AsteroidModel


@Dao
interface AsteroidDataBaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg item: AsteroidModel)

    @Query("SELECT * FROM Asteroid_table ORDER BY closeApproachDate")
    fun getAsteroidList(): LiveData<List<AsteroidModel>>

    @Query("DELETE FROM Asteroid_table")
    suspend fun clear()

}

@Database(entities = [AsteroidModel::class], version = 1, exportSchema = false)
abstract class AsteroidDataBase : RoomDatabase() {
    abstract val dao: AsteroidDataBaseDAO
}

@Volatile
private lateinit var asteroidDataBase: AsteroidDataBase

fun getAsteroidDatabaseDAOInstance(context: Context): AsteroidDataBaseDAO {
    synchronized(AsteroidDataBase::class.java) {
        if (!::asteroidDataBase.isInitialized ) {
            asteroidDataBase = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDataBase::class.java,
                "AsteroidDatabase"
            ).fallbackToDestructiveMigration().build()
        }
        return asteroidDataBase.dao
    }
}
