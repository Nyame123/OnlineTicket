package com.bismark.onlineticket.data_layer.converters

import androidx.room.TypeConverter
import java.util.*

class DataTypeConverter {

    companion object {
        @TypeConverter
        @JvmStatic
        fun toDate(date: Long): Date {
            return Date(date)
        }

        @TypeConverter
        @JvmStatic
        fun toLong(date: Date): Long {
            return date.time
        }
    }
}
