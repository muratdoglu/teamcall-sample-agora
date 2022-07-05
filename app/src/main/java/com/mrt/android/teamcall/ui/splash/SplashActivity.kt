package com.mrt.android.teamcall.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.mrt.android.teamcall.R
import com.mrt.android.teamcall.ui.login.LoginActivity
import com.mrt.android.teamcall.ui.main.MainActivity
import com.mrt.android.teamcall.util.FirebaseManager
import com.taksim.android.extentions.get
import com.taksim.android.extentions.set
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {
    val pref: SharedPreferences by inject()
    val firebaseManager: FirebaseManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        animationLoader()
        Handler().postDelayed({
            if (pref["userNick", ""] != "") {
                firebaseManager.nick = pref["userNick"] ?: ""
                firebaseManager.updateFreeState(true)
                goToMainActivity()
            } else {
                goToLoginActivity()
            }

        }, 2000)
    }


    private fun animationLoader() {
        YoYo.with(Techniques.Bounce)
            .duration(700)
            .repeat(5)
            .playOn(findViewById(R.id.ivLogo))
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}