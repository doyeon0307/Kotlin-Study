package com.example.mysololife.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FBTime {

    companion object {
        fun getTime():String {

            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(currentDateTime)

            return dateFormat

        }
    }

}