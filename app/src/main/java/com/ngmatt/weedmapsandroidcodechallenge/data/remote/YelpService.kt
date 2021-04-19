package com.ngmatt.weedmapsandroidcodechallenge.data.remote

import com.ngmatt.weedmapsandroidcodechallenge.data.remote.response.ReviewsResponse
import com.ngmatt.weedmapsandroidcodechallenge.data.remote.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface YelpService {
    // Normally I would add an interceptor for auth tokens, but it seemed like overkill for two
    // api calls
    @GET(Endpoints.SEARCH)
    suspend fun search(
        @Header("Authorization") key: String,
        @Query("term") term: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("offset") offset: Int
    ): SearchResponse

    @GET(Endpoints.REVIEWS)
    suspend fun getBusinessReviews(
        @Header("Authorization") key: String, @Path("id") id: String
    ): ReviewsResponse
}
