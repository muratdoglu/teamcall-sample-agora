package com.mrt.android.teamcall.ui.call

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mrt.android.teamcall.R
import com.mrt.android.teamcall.databinding.ActivityCallBinding
import com.mrt.android.teamcall.ui.chat.VideoChatViewActivity
import kotlinx.android.synthetic.main.activity_call.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class CallActivity : AppCompatActivity() {
    private val callViewModel: CallViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCallBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_call)
        callViewModel.getUserDetail()
        binding.vm = callViewModel
        val wind: Window = this.window
        wind.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        wind.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        wind.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        ring()
        load()
        listeners()

    }

    private fun ring() {
        val mp: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.call)
        mp.start()
    }

    private fun listeners() {
        callViewModel.tvCallerNick.observe(this, Observer {
            tvTitle.text = "$it is calling!"
        })
        btAccept.setOnClickListener {
            val intent = Intent(this, VideoChatViewActivity::class.java)
            intent.putExtra(
                "channelId", callViewModel.callerUID)
            startActivity(intent)
            finish()
        }
        btReject.setOnClickListener {
           finish()
        }
    }

    override fun onBackPressed() {

    }

    private fun load() {
        var intent = intent
    }
}