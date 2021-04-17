package com.ngmatt.weedmapsandroidcodechallenge.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Region(var center: Center? = null)
