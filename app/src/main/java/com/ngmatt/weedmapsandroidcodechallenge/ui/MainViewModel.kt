package com.ngmatt.weedmapsandroidcodechallenge.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngmatt.weedmapsandroidcodechallenge.data.Loading
import com.ngmatt.weedmapsandroidcodechallenge.data.repository.BusinessRepo
import com.ngmatt.weedmapsandroidcodechallenge.utils.awaitLastLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

//TODO change to app context
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: BusinessRepo,
    @ApplicationContext private val context: Context
) :
    ViewModel() {

    fun search(term: String) = flow {
        val currentLocation = context.awaitLastLocation()
        val searchAndReviewsList = repo.search(
            term,
            currentLocation.latitude.toString(),
            currentLocation.longitude.toString()
        )
            .map { business ->
                val topReview = business.id?.let {
                    repo.getBusinessReviews(business.id)
                        .maxByOrNull { review -> review.rating ?: 0 }
                }
                SearchAndReview(business, topReview)
            }
        emit(searchAndReviewsList)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        Loading
    )
}
