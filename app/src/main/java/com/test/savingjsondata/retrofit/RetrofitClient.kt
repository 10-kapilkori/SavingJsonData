package com.test.savingjsondata.retrofit

import com.google.gson.GsonBuilder
import com.test.savingjsondata.retrofit.API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val MainUrl = "https://jsonplaceholder.typicode.com"

    //    Initializing the "retrofitClient"
    private val retrofitClient: Retrofit.Builder by lazy {
        //        Adding the Http logging to see the results in the logcat
        val levelType: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val okhttpClient = OkHttpClient.Builder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)

        okhttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(MainUrl)
            .client(okhttpClient.build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
    }

    //    Initializing the "apiInterface"
    val apiInterface: API by lazy { retrofitClient.build().create(API::class.java) }
}