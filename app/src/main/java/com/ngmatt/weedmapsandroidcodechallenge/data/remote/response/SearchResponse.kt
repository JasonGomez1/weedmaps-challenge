package com.ngmatt.weedmapsandroidcodechallenge.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    var total: Int? = null,
    var businesses: List<Business>? = null,
    var region: Region? = null
)
