package com.example.myapplication

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
//Просто запрос на логин
interface Request {
    @POST("api/login")
    @FormUrlEncoded
    fun registrationPost(@Field("username") email: String,
                         @Field("password") password: String): Call<Login>

}
