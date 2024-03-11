package com.ryanpatrick.mathhammer40k.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ryanpatrick.mathhammer40k.data.Profile

@Database(entities = [Profile::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ProfileDatabase: RoomDatabase(){
    abstract fun profileDao(): ProfileDao
}