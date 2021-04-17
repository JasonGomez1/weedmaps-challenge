package com.ngmatt.weedmapsandroidcodechallenge.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(var alias: String? = null, var title: String? = null)
