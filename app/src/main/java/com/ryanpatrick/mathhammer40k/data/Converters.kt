package com.ryanpatrick.mathhammer40k.data

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters {
    @TypeConverter
    fun fromStringToWeaponList(string: String): List<Weapon>{
        val listType = object : TypeToken<List<Weapon?>?>() {}.type
        return Gson().fromJson(string, listType)
    }
    @TypeConverter
    fun fromWeaponListToString(list: List<Weapon>): String{
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToKeywordList(string: String): List<Keywords>{
        val listType = object : TypeToken<List<Keywords?>?>() {}.type
        return Gson().fromJson(string, listType)
    }

    @TypeConverter
    fun fromKeywordListToString(list: List<Keywords>): String{
        val gson = Gson()
        return gson.toJson(list)
    }
}