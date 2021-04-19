package com.ngmatt.weedmapsandroidcodechallenge.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngmatt.weedmapsandroidcodechallenge.data.*
import com.ngmatt.weedmapsandroidcodechallenge.data.repository.BusinessRepo
import com.ngmatt.weedmapsandroidcodechallenge.utils.awaitLastLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: BusinessRepo,
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    private val _searchViewState = MutableStateFlow<SearchViewState>(Initial)
    val searchViewState = _searchViewState.asStateFlow()

    fun search(term: String, offset: Int = 0) {
        viewModelScope.launch {
            _searchViewState.value = Loading
            _searchViewState.value = try {
                val currentLocation = context.awaitLastLocation()
                val searchAndReviewsList = repo.search(
                    term,
                    currentLocation.latitude.toString(),
                    currentLocation.longitude.toString(),
                    offset
                )
                    .map { business ->
                        val topReview = repo.getBusinessReviews(business.id)
                            .maxByOrNull { review -> review.rating }
                        SearchAndTopReview(business, topReview)
                    }
                SearchLoaded(searchAndReviewsList)
            } catch (e: Exception) {
                Error
            }
        }
    }
}
