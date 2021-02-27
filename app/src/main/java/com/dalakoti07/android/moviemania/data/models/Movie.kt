package com.dalakoti07.android.moviemania.data.models

import java.io.Serializable


/**
{
"year": 2004,
"title": "Little Black Book",
"info": {
"directors": ["Nick Hurran"],
"release_date": "2004-08-06T00:00:00Z",
"rating": 5.1,
"genres": [
"Comedy",
"Romance",
"Drama"
],
"image_url": "https://m.media-amazon.com/images/M/MV5BMTY0ODczNzM4OV5BMl5BanBnXkFtZTcwNzcyMDUzMw@@._V1_SX400_.jpg",
"plot": "A woman snoops through her boyfriend's palm pilot and reveals his former girlfriends, which causes her to question why they're still listed in his little black book.",
"rank": 5000,
"running_time_secs": 6660,
"actors": [
"Brittany Murphy",
"Ron Livingston",
"Holly Hunter"
]
}
}
 **/
data class Movie(
    val info: Info,
    val title: String,
    val year: Int
):Serializable