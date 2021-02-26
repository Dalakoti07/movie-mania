package com.dalakoti07.android.moviemania.data.models

data class Info(
    val actors: List<String>,
    val directors: List<String>,
    val genres: List<String>,
    val image_url: String,
    val plot: String,
    val rank: Int,
    val rating: Double,
    val release_date: String,
    val running_time_secs: Int
)