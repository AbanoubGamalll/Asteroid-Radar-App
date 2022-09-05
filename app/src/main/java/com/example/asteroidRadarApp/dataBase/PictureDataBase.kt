package com.example.asteroidRadarApp.dataBase


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.asteroidRadarApp.model.PictureOfDayModel


@Dao
interface PictureDataBaseDAO {
    @Insert
    suspend fun insert(item: PictureOfDayModel)

    @Query("SELECT * FROM Picture_table")
    fun getPicture(): LiveData<PictureOfDayModel>

    @Query("DELETE FROM Picture_table")
    suspend fun clear()

}

@Database(entities = [PictureOfDayModel::class], version = 1, exportSchema = false)
abstract class PictureDataBase : RoomDatabase() {
    abstract val dao: PictureDataBaseDAO
}

@Volatile
private lateinit var pictureDataBase: PictureDataBase

fun getPictureDatabaseDAOInstance(context: Context): PictureDataBaseDAO {
    synchronized(PictureDataBase::class.java) {
        if (!::pictureDataBase.isInitialized ) {
            pictureDataBase = Room.databaseBuilder(
                context.applicationContext,
                PictureDataBase::class.java,
                "PictureDatabase"
            ).fallbackToDestructiveMigration().build()
        }
        return pictureDataBase.dao
    }
}
