package com.ryanpatrick.mathhammer40k

import android.content.Context
import com.ryanpatrick.mathhammer40k.room.ProfileDatabase
import com.ryanpatrick.mathhammer40k.room.ProfileRepository
import kotlinx.coroutines.CoroutineScope

object Graph {
    lateinit var database: ProfileDatabase

    val profileRepository by lazy{
        ProfileRepository(profileDao = database.profileDao())
    }

    fun provide(context: Context, scope: CoroutineScope){
        database = ProfileDatabase.getDatabase(context, scope)
    }
}