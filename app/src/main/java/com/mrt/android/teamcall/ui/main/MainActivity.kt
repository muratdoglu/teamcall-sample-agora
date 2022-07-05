package com.mrt.android.teamcall.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrt.android.teamcall.R
import com.mrt.android.teamcall.data.model.User
import com.mrt.android.teamcall.ui.call.CallActivity
import com.mrt.android.teamcall.ui.chat.VideoChatViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listeners()
        mainViewModel.observeStart()
    }

    private fun listeners() {
        mainViewModel.firebaseManager.userList.observe(this, Observer {
            val userListAdapter = UserListAdapter(this, it) {
                if(it.free){
                    call(it)
                }
            }
            rvUserList.layoutManager = LinearLayoutManager(this)
            rvUserList.adapter = userListAdapter
        })

    }

    private fun call(it: User) {
        if(it.free){
            val intent = Intent(this, VideoChatViewActivity::class.java)
            intent.putExtra("channelId", Settings.Secure.getString(
                contentResolver,
                Settings.Secure.ANDROID_ID
            ))
            mainViewModel.firebaseManager.updateCaller(it)
            mainViewModel.sendNotification(it)
            startActivity(intent)
        }

    }
}