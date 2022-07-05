package com.mrt.android.teamcall.ui.login

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mrt.android.teamcall.module.data.MainRepository
import androidx.lifecycle.ViewModel
import com.mrt.android.teamcall.util.FirebaseManager
import com.taksim.android.extentions.set

class LoginViewModel(val pref: SharedPreferences, val firebaseManager: FirebaseManager) :
    ViewModel() {
    var _etNick = MutableLiveData<String>()
    var loading = MutableLiveData(false)
    var loginSuccess = MutableLiveData(false)
    var pushNotificationToken = ""

    private val _etShakeAnimation = MutableLiveData<Boolean>()
    val etShakeAnimation: LiveData<Boolean> get() = _etShakeAnimation

    fun loginClick(view: View) {
        var nick = _etNick.value
        nick?.let {
            loading.postValue(true)
            firebaseManager.register(it, pushNotificationToken) {
                pref["userNick"] = nick
                loading.postValue(false)
                loginSuccess.postValue(true)
            }
        } ?: run {
            _etShakeAnimation.postValue(true)
            loading.postValue(false)
        }

    }
}