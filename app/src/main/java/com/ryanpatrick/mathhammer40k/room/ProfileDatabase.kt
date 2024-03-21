package com.ryanpatrick.mathhammer40k.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ryanpatrick.mathhammer40k.data.Profile
import com.ryanpatrick.mathhammer40k.data.guardsmanEquivalent
import com.ryanpatrick.mathhammer40k.data.spaceMarineEquivalent
import com.ryanpatrick.mathhammer40k.data.terminatorEquivalent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Profile::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ProfileDatabase: RoomDatabase(){
    abstract fun profileDao(): ProfileDao

    companion object{
        @Volatile private var INSTANCE: ProfileDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ProfileDatabase =
            INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context, ProfileDatabase::class.java, "profiles.db")
                    .addCallback(ProfileDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
    }

    private class ProfileDatabaseCallback(private val scope: CoroutineScope): Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let{database->
                scope.launch {
                    populateDatabase(database.profileDao())
                }
            }
        }

        suspend fun populateDatabase(profileDao: ProfileDao){
            profileDao.insertProfile(spaceMarineEquivalent)
            profileDao.insertProfile(guardsmanEquivalent)
            profileDao.insertProfile(terminatorEquivalent)
        }
    }
}

