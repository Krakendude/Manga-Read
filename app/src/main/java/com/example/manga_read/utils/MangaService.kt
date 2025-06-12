package com.example.manga_read.utils

import com.example.manga_read.data.CoverResponse
import com.example.manga_read.data.MangaResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MangaService {

    @GET("manga")
    suspend fun searchManga(@Query("title") title: String): MangaResponse

    @GET("cover")
    suspend fun getCover(@Query("manga[]") mangaId: String): CoverResponse

    companion object {
        fun getInstance(): MangaService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.mangadex.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MangaService::class.java)
        }
    }
}