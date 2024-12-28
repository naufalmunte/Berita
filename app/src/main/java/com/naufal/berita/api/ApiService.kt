package com.naufal.berita.api

import com.naufal.berita.model.AddBeritaResponse
import com.naufal.berita.model.BeritaResponse
import com.naufal.berita.model.LoginResponse
import com.naufal.berita.model.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("api_basic/register.php")
    fun register(
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("fullname") fullname:String,
        @Field("email") email:String

    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("api_basic/login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("api_basic/get_berita.php")
    fun getListBerita(@Query("judul") judul: String): Call<BeritaResponse>

    @Multipart
    @POST("api_basic/add_berita.php")
    fun addBerita(
        @Part("judul") judul: RequestBody,
        @Part("isi") isi: RequestBody,
        @Part filegambar: MultipartBody.Part
    ): Call<AddBeritaResponse>

}