package com.example.asteroidRadarApp.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity(tableName = "Picture_table")
data class PictureOfDayModel(
    @PrimaryKey(autoGenerate = true)
    val id :Long=0L,
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    val url: String)


enum class State { Loading, Done }