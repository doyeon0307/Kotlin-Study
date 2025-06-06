package com.example.booksearch.data.db

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class OrmConverter {

    @TypeConverter
    fun fromList(value: List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)

}