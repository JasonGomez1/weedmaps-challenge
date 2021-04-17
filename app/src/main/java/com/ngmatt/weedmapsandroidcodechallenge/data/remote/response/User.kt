package com.ngmatt.weedmapsandroidcodechallenge.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    var id: String? = null,
    var name: String? = null,
    @Json(name = "profile_url")
    var profileUrl: String? = null,
    @Json(name = "image_url")
    var imageUrl: String? = null
)
