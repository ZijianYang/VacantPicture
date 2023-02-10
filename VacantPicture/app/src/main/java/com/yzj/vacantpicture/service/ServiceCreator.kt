package com.yzj.vacantpicture.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceCreator {
    object ServiceCreator {

        private const val BASE_URL = "http://5ee00ea2.r7.cpolar.top/api/"
        //private const val BASE_URL = "http://localhost:5047/api/"

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

        inline fun <reified T> create(): T = create(T::class.java)
    }
}