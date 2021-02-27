package com.dalakoti07.android.moviemania.utils

import com.dalakoti07.android.moviemania.data.models.Movie

class YearWiseDataComparators {

    companion object :Comparator<Movie>{
        override fun compare(p0: Movie, p1: Movie): Int =when{
            p0.year== p1.year -> p0.title.compareTo(p1.title)
            else -> p0.year-p1.year
        }
    }
}

class NameWiseDataComparators{
    companion object:Comparator<Movie>{
        override fun compare(p0: Movie, p1: Movie): Int =when{
            else -> p0.title.compareTo(p1.title)
        }
    }
}