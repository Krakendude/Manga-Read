package com.example.manga_read.data

data class MangaResponse(val data: List<MangaItem>)

data class MangaItem(
    val id: String,
    val attributes: MangaAttributes
)

data class MangaAttributes(
    val title: Map<String, String> // Ej: { "en": "Naruto" }
)

data class CoverResponse(val data: List<CoverData>)
data class CoverData(
    val attributes: CoverAttributes
)
data class CoverAttributes(
    val fileName: String
)
