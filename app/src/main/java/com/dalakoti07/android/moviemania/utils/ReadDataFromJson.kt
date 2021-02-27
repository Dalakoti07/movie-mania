package com.dalakoti07.android.moviemania.utils

import android.content.Context
import kotlinx.coroutines.*
import java.io.IOException

class ReadDataFromJson {

    companion object{
        fun getJsonDataFromAsset(context: Context, fileName: String) =
            CoroutineScope(Dispatchers.IO).async{
                var jsonString: String? = null
                try {
                    delay(1000)
                    jsonString =
                        context.assets.open(fileName).bufferedReader().use { it.readText() }
                } catch (ioException: IOException) {
                    ioException.printStackTrace()
                }
                return@async jsonString
            }
    }
}