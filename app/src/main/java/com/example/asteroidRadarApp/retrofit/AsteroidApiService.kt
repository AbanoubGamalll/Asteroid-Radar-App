package com.example.asteroidRadarApp.retrofit

import com.example.asteroidRadarApp.model.PictureOfDayModel
import com.example.asteroidRadarApp.until.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


interface AsteroidApiService {
    @GET(Constants.PICTURE_OF_DAY_API)
    suspend fun getTodayAsteroid(): PictureOfDayModel

    @GET(Constants.ALL_DAY_API)
    suspend fun getAllDayAsteroid(): String
}

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

private lateinit var asteroidApiService: AsteroidApiService
fun getAsteroidAPIInstance(): AsteroidApiService {
    if (!(::asteroidApiService.isInitialized)) {
        asteroidApiService = retrofit.create(AsteroidApiService::class.java)
    }
    return asteroidApiService
}


