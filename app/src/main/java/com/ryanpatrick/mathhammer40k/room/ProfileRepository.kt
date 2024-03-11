package com.ryanpatrick.mathhammer40k.room

import com.ryanpatrick.mathhammer40k.data.Profile
import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val profileDao: ProfileDao) {
    suspend fun addProfile(profile: Profile){
        profileDao.insertProfile(profile)
    }

    fun getAllProfiles(): Flow<List<Profile>> = profileDao.getAllProfiles()

    fun getProfileById(id: Long): Flow<Profile>{
        return profileDao.getProfileById(id)
    }

    suspend fun updateProfile(profile: Profile){
        profileDao.updateProfile(profile)
    }

    suspend fun deleteProfile(profile: Profile){
        profileDao.deleteProfile(profile)
    }

}