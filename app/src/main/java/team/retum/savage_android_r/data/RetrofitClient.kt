package team.retum.savage_android_r.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.retum.savage_android_r.data.api.AuthApi
import team.retum.savage_android_r.data.api.MainApi
import team.retum.savage_android_r.data.api.PostApi
import team.retum.savage_android_r.data.api.UserApi
import team.retum.savage_android_r.data.interceptor.Interceptor
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(Interceptor())
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://172.16.24.136:1313/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    val authApi by lazy { retrofit.create(AuthApi::class.java) }
    val mainApi by lazy { retrofit.create(MainApi::class.java) }
    val postApi by lazy { retrofit.create(PostApi::class.java) }
    val userApi by lazy { retrofit.create(UserApi::class.java) }

}