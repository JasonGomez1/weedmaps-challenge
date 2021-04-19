package com.ngmatt.weedmapsandroidcodechallenge.ui

import com.ngmatt.weedmapsandroidcodechallenge.data.remote.response.Business
import com.ngmatt.weedmapsandroidcodechallenge.data.remote.response.Review

data class SearchAndTopReview(val business: Business, val review: Review?)
