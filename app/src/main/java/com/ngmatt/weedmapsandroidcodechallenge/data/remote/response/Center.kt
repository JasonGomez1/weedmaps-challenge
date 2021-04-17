package com.ngmatt.weedmapsandroidcodechallenge.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Center(var latitude: Double? = null, var longitude: Double? = null)
