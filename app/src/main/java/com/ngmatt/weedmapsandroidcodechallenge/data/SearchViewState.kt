package com.ngmatt.weedmapsandroidcodechallenge.data

import com.ngmatt.weedmapsandroidcodechallenge.ui.SearchAndReview

sealed class SearchViewState
object Loading : SearchViewState()
object Error : SearchViewState()
data class SearchLoaded(val searchResult: List<SearchAndReview>)