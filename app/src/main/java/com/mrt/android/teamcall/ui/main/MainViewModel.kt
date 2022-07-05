package com.mrt.android.teamcall.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.mrt.android.teamcall.module.data.MainRepository
import androidx.lifecycle.ViewModel
import com.mrt.android.teamcall.data.model.User
import com.mrt.android.teamcall.util.FirebaseManager

class MainViewModel( val pref : SharedPreferences, val firebaseManager: FirebaseManager, val mainRepository: MainRepository) : ViewModel() {

    fun observeStart(){
        firebaseManager.observeUsers()
    }

    fun sendNotification(user: User) {
        mainRepository.sendNotification(user.token,{

        },{

        },{

        })
    }
}