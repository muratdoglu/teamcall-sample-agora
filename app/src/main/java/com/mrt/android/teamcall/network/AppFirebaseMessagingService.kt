package com.mrt.android.teamcall.network

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mrt.android.teamcall.ui.call.CallActivity
import com.mrt.android.teamcall.ui.main.MainActivity


class AppFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val intent = Intent(this, CallActivity::class.java)
        startActivity(intent)
    }

}