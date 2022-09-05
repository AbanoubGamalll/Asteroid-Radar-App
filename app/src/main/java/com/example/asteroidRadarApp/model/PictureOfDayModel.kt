package com.example.asteroidRadarApp.model

import com.squareup.moshi.Json



data class PictureOfDayModel(
    @Json(name = "media_type")
    val mediaType: String = "",
    val title: String = "Loading...",
    val url: String = "",
    var state: State = State.Loading
)

enum class State { Loading, Error, Done ,Finish}