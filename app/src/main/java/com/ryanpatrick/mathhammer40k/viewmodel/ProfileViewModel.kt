package com.ryanpatrick.mathhammer40k.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanpatrick.mathhammer40k.Graph
import com.ryanpatrick.mathhammer40k.data.Profile
import com.ryanpatrick.mathhammer40k.data.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository = Graph.profileRepository): ViewModel() {
    lateinit var getAllProfiles: Flow<List<Profile>>

    init{
        viewModelScope.launch {
            getAllProfiles = profileRepository.getAllProfiles()
        }
    }

    fun addProfile(profile: Profile){
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.addProfile(profile)
        }
    }

    fun updateProfile(profile: Profile){
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.updateProfile(profile)
        }
    }

    fun getProfileById(id: Long): Flow<Profile>{
        return profileRepository.getProfileById(id)
    }

    fun deleteProfile(profile: Profile){
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.deleteProfile(profile)
        }
    }
}