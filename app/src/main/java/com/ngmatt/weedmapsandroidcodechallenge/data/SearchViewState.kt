package com.ngmatt.weedmapsandroidcodechallenge.data

import com.ngmatt.weedmapsandroidcodechallenge.ui.SearchAndTopReview

/**
 * Sealed class representing all the possible states the screen could be in
 */
sealed class SearchViewState
object Loading : SearchViewState()
object Error : SearchViewState()
object Initial: SearchViewState()
data class SearchLoaded(val searchResult: List<SearchAndTopReview>) : SearchViewState()