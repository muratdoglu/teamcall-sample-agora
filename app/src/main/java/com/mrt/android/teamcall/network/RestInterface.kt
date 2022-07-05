package com.mrt.android.ruutapp.network
import com.mrt.android.teamcall.data.model.User
import com.mrt.android.teamcall.module.data.SendNotificationModel
import retrofit2.Call
import retrofit2.http.*

interface RestInterface {

    @POST("send")
    fun sendNotification(@Body sendNotificationModel: SendNotificationModel): Call<String>
}