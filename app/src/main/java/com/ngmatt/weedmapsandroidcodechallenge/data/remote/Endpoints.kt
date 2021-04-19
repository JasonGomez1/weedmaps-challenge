package com.ngmatt.weedmapsandroidcodechallenge.data.remote

object Endpoints {
    const val BASE_URL = "https://api.yelp.com/v3/"
    const val SEARCH = "businesses/search"
    const val REVIEWS = "businesses/{id}/reviews"
    // I added the api key here because it was expedient--I wouldn't do this for production code
    const val API_KEY = "Bearer 84bcwQvxrUAeRsHV5Hf0Juhq3gZ4AtM-8KBpRywnP464V4y_WrOqraeE7CBUxxovGIBf-TAqQ7GHM40XoZID5xu9nWI40pgGtjjvdN-AMYpMj6UDLQ2JdBvb_Wx4YHYx"
}
