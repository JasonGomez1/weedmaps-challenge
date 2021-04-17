package com.ngmatt.weedmapsandroidcodechallenge.ui

import com.ngmatt.weedmapsandroidcodechallenge.data.remote.response.Business
import com.ngmatt.weedmapsandroidcodechallenge.data.remote.response.Review

// TODO think of a better name for this class
data class SearchAndReview(val business: Business, val review: Review?)
