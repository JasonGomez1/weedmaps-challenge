package com.ngmatt.weedmapsandroidcodechallenge.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    var city: String? = null,
    var country: String? = null,
    var address2: String? = null,
    var address3: String? = null,
    var state: String? = null,
    var address1: String? = null,
    @Json(name = "zip_code")
    var zipCode: String? = null
)
