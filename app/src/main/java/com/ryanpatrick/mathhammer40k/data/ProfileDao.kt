package com.ryanpatrick.mathhammer40k.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ProfileDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertProfile(profile: Profile)

    @Update
    abstract suspend fun updateProfile(profile: Profile)

    @Delete
    abstract suspend fun deleteProfile(profile: Profile)

    @Query("SELECT * FROM `profile_table`")
    abstract fun getAllProfiles(): Flow<List<Profile>>

    @Query("SELECT * FROM `profile_table` WHERE id = :id")
    abstract fun getProfileById(id: Long): Flow<Profile>

}