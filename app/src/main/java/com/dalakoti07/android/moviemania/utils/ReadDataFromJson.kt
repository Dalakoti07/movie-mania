package com.dalakoti07.android.moviemania.utils

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class ReadDataFromJson {

    companion object{
        suspend fun getJsonDataFromAsset(context: Context, fileName: String): String? {
            var jsonString: String?=null
            GlobalScope.launch(Dispatchers.IO) {
                try {
//                Thread.sleep(11000)
                    jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
                } catch (ioException: IOException) {
                    ioException.printStackTrace()
                }
            }
            return jsonString
        }
    }
}