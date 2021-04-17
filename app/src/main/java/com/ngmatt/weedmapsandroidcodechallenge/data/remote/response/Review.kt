package com.ngmatt.weedmapsandroidcodechallenge.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Review(
    var id: String? = null,
    var rating: Int? = null,
    var user: User? = null,
    var text: String? = null,
    var url: String? = null,
    @Json(name = "time_created")
    var timeCreated: String? = null
)
