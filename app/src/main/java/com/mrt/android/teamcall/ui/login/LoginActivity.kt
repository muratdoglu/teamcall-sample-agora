package com.mrt.android.teamcall.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.mrt.android.teamcall.R
import com.mrt.android.teamcall.databinding.ActivityLoginBinding
import com.mrt.android.teamcall.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_login)
        binding.vm = loginViewModel
        listeners()
    }

    private fun listeners() {
        loginViewModel.etShakeAnimation.observe(this) {
            YoYo.with(Techniques.Shake)
                .duration(500)
                .playOn(findViewById(R.id.etNick))
        }
        loginViewModel.loading.observe(this) {
            if (it) {
                rlLoading.visibility = View.VISIBLE
            } else {
                rlLoading.visibility = View.GONE
            }
        }
        loginViewModel.loginSuccess.observe(this, Observer {
            if (it)
                goToMainActivity()
        })
        getToken()
    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                loginViewModel.pushNotificationToken = task.result
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}