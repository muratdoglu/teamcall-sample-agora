package com.mrt.android.teamcall.ui.call

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mrt.android.teamcall.data.model.User
import com.mrt.android.teamcall.util.FirebaseManager
import com.taksim.android.extentions.set


class CallViewModel(val pref: SharedPreferences, val firebaseManager: FirebaseManager) :
    ViewModel() {
    var tvCallerNick = MutableLiveData<String>()
    var callerUID: String? = null
    fun getUserDetail() {
        firebaseManager.getUserDetail {
            callerUID = it.caller
            tvCallerNick.postValue(it.callerName)
        }
    }
}