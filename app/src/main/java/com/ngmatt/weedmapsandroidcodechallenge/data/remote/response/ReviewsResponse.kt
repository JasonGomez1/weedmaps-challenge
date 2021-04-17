package com.ngmatt.weedmapsandroidcodechallenge.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewsResponse(
    var reviews: List<Review>? = null,
    var total: Int? = null,
    @Json(name = "possible_languages")
    var possibleLanguages: List<String>? = null
)
