package com.ngmatt.weedmapsandroidcodechallenge.data.repository

import com.ngmatt.weedmapsandroidcodechallenge.data.remote.response.Business
import com.ngmatt.weedmapsandroidcodechallenge.data.remote.response.Review

interface BusinessRepo {

    suspend fun search(term: String, latitude: String, longitude: String): List<Business>

    suspend fun getBusinessReviews(id: String): List<Review>
}
