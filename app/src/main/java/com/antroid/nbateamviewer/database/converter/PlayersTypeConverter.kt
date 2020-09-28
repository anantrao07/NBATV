package com.antroid.nbateamviewer.database.converter

import androidx.room.TypeConverter
import com.antroid.nbateamviewer.model.Player
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PlayersTypeConverter {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun stringToList(data: String?): List<Player>{
        if (data.isNullOrBlank()){
            return emptyList()
        }
        val listType = object : TypeToken<List<Player>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    @JvmStatic
    fun listToString(someObject: List<Player>): String {
        return gson.toJson(someObject)
    }
}