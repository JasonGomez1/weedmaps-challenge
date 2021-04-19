package com.ngmatt.weedmapsandroidcodechallenge.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Business(
    val rating: Double? = null,
    val price: String? = null,
    val phone: String? = null,
    val id: String? = null,
    val alias: String? = null,
    val categories: List<Category>? = null,
    val name: String? = null,
    val url: String? = null,
    val coordinates: Coordinates? = null,
    val location: Location? = null,
    val distance: Double? = null,
    val transactions: List<String>? = null,
    @Json(name = "image_url")
    val imageUrl: String? = null,
    @Json(name = "review_count")
    val reviewCount: Int? = null,
    @Json(name = "is_closed")
    val isClosed: Boolean? = null
)