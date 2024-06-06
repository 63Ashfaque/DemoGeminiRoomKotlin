package com.ashfaque.demogeminiroomkotlin

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

class Utils {

    @SuppressLint("SimpleDateFormat")
     fun getCurrentDateTime(format:String?):String
    {
        if(format.isNullOrBlank())
        {
            return ""
        }
        val dateFormat = SimpleDateFormat(format)
        val date = Date()
        return dateFormat.format(date)
    }
}