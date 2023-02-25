package com.example.myapplication.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    const val baseUrl="https://api.jsonbin.io/v3/"

    fun getDateFormat(date:Date, dateFormat:String="yyyy-MM-dd"):String{

        val simpleDateFormat = SimpleDateFormat(dateFormat)
        simpleDateFormat.timeZone = TimeZone.getDefault()
        val localTime: String = simpleDateFormat.format(date)
        Log.d("TAG", "Local time: $localTime")
        return  simpleDateFormat.format(date)
    }

    fun getDate(str: String?, str2: String?): Date? {
        return try {
            try {
                SimpleDateFormat(str2).parse(str)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } catch (e2: Exception) {
            e2.printStackTrace()
            null
        }
    }
}