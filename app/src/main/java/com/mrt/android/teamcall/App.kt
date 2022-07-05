package com.mrt.android.teamcall

import android.app.Application
import com.mrt.android.teamcall.ui.mainModule
import com.mrt.android.teamcall.ui.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, mainModule))
            androidLogger()

        }
    }
}