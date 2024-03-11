package com.ryanpatrick.mathhammer40k

import android.content.Context
import androidx.room.Room
import com.ryanpatrick.mathhammer40k.data.ProfileDatabase
import com.ryanpatrick.mathhammer40k.data.ProfileRepository

object Graph {
    lateinit var database: ProfileDatabase

    val profileRepository by lazy{
        ProfileRepository(profileDao = database.profileDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, ProfileDatabase::class.java, "profiles.db").build()
    }
}