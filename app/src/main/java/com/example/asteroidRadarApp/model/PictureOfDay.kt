package com.example.asteroidRadarApp.model


data class PictureOfDay(/*@Json(name = "media_type")*/ val mediaType: String, val title: String,
                        val url: String)