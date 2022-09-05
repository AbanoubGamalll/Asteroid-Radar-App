package com.example.asteroidRadarApp.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidRadarApp.R
import com.example.asteroidRadarApp.dataBase.getPictureDatabaseDAOInstance
import com.example.asteroidRadarApp.retrofit.getAsteroidAPIInstance
import com.squareup.picasso.Picasso


class RefreshDataWorker
    (private val context: Context, private val params: WorkerParameters) :
    CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val PictureDP = getPictureDatabaseDAOInstance(context)
        Log.i("asd","Run BackGround")
        return try {
            val pictureOfDayModel = getAsteroidAPIInstance().getTodayAsteroid()
            PictureDP.clear()
            PictureDP.insert(pictureOfDayModel)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}