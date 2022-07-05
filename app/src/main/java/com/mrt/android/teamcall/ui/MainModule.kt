package com.mrt.android.teamcall.ui

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.mrt.android.ruutapp.network.RestInterface
import com.mrt.android.teamcall.module.data.MainRepository
import com.mrt.android.teamcall.ui.call.CallViewModel
import com.mrt.android.teamcall.ui.login.LoginViewModel
import com.mrt.android.teamcall.ui.main.MainViewModel
import com.mrt.android.teamcall.util.FirebaseManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val mainModule = module {
    single {
        getSharedPrefs(androidApplication())
    }
    single { MainRepository(get()) }
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { CallViewModel(get(), get()) }
    single { FirebaseManager(get()) }
}

val networkModule = module {
    factory<Interceptor> {
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("API", it) })
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    factory {
        val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().protocols(arrayListOf(Protocol.HTTP_1_1))
            .addInterceptor(httpLoggingInterceptor).addInterceptor { chain ->
            val request = chain.request().newBuilder().header(
                "Authorization",
                "key=AAAAr7FuLGk:APA91bEHFnq__lvguplnQ15J7rTVhlqZMO5au-IhJZREBwEKbEE_4ptCzF-BvgOVA7_VV4cSrqlR4p2gmONuzobHlXuQK8L7qGlWOQDP4GDh32ivnOOp8wmO40S-FKc14hzEfzmdkZ4V"
            ).build()
            chain.proceed(request)
        }.build()
    }

    single {
        val httpClient = OkHttpClient.Builder()
        Retrofit.Builder()
            .client(get())
            .baseUrl(" https://fcm.googleapis.com/fcm/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(RestInterface::class.java) }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences(
        androidApplication.packageName,
        android.content.Context.MODE_PRIVATE
    )
}