package com.example.booksearch.data.api

import com.example.booksearch.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// object + lazy 조합 -> 사용되는 순간에만 만들어짐 / 싱글톤
object RetrofitInstance {

    private val okHttpClient: OkHttpClient by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    // 객체 생성
    // client에 인터셉터를 넘겨주어, 로그캣에서 패킷 내용 모니터링
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    // 인스턴스 생성
    val api: BookSearchApi by lazy {
        retrofit.create(BookSearchApi::class.java)
    }

}