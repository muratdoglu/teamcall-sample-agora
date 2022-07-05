package com.mrt.android.teamcall.module.data

import android.util.Log
import com.mrt.android.ruutapp.network.RestInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(val repo: RestInterface) {
    inline fun sendNotification(token: String,
                          crossinline successHandler: (String) -> Unit,
                          crossinline failureHandler: (Throwable?) -> Unit,
                          crossinline errorHandler: (String) -> Unit) {
        repo.sendNotification(SendNotificationModel(token)).enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
}


