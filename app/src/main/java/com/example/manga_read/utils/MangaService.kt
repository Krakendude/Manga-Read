package com.example.manga_read.utils

import com.example.manga_read.data.MangaResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface mangaService {

    @GET("manga")
    suspend fun searchManga(@Query("title") title: String): MangaResponse

    companion object {
        fun getInstance(): mangaService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.mangadex.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(mangaService::class.java)
        }
    }
}