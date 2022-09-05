package com.example.asteroidRadarApp.until

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"

    // TODO Add Your Key Here in API_KEY variable
    private const val API_KEY = ""

    const val PICTURE_OF_DAY_API = "planetary/apod?api_key=${API_KEY}"
    const val ALL_DAY_API = "neo/rest/v1/feed?api_key=${API_KEY}"
}