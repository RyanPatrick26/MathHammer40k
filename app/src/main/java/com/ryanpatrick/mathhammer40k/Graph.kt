package com.ryanpatrick.mathhammer40k

import android.content.Context
import androidx.room.Room
import com.ryanpatrick.mathhammer40k.room.Converters
import com.ryanpatrick.mathhammer40k.room.ProfileDatabase
import com.ryanpatrick.mathhammer40k.room.ProfileRepository

object Graph {
    val converters = Converters()
    lateinit var database: ProfileDatabase

    val profileRepository by lazy{
        ProfileRepository(profileDao = database.profileDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, ProfileDatabase::class.java, "profiles.db").build()
    }
}