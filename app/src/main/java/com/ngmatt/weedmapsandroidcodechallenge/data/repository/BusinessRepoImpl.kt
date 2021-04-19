package com.ngmatt.weedmapsandroidcodechallenge.data.repository

import com.ngmatt.weedmapsandroidcodechallenge.data.remote.Endpoints
import com.ngmatt.weedmapsandroidcodechallenge.data.remote.YelpService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusinessRepoImpl @Inject constructor(private val service: YelpService) : BusinessRepo {

    override suspend fun search(term: String, latitude: String, longitude: String, offset: Int) =
        service.search(Endpoints.API_KEY, term, latitude, longitude, offset).businesses ?: emptyList()

    override suspend fun getBusinessReviews(id: String) =
        service.getBusinessReviews(Endpoints.API_KEY, id).reviews ?: emptyList()
}
