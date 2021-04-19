package com.ngmatt.weedmapsandroidcodechallenge

import android.content.Context
import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.ngmatt.weedmapsandroidcodechallenge.data.Initial
import com.ngmatt.weedmapsandroidcodechallenge.data.Loading
import com.ngmatt.weedmapsandroidcodechallenge.data.SearchLoaded
import com.ngmatt.weedmapsandroidcodechallenge.data.SearchViewState
import com.ngmatt.weedmapsandroidcodechallenge.data.remote.response.Business
import com.ngmatt.weedmapsandroidcodechallenge.data.remote.response.Review
import com.ngmatt.weedmapsandroidcodechallenge.data.repository.BusinessRepo
import com.ngmatt.weedmapsandroidcodechallenge.ui.SearchViewModel
import com.ngmatt.weedmapsandroidcodechallenge.utils.awaitLastLocation
import com.nhaarman.mockitokotlin2.mock
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SearchViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var mockBusinessRepo: BusinessRepo

    @MockK
    lateinit var mockContext: Context
    lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = SearchViewModel(mockBusinessRepo, mockContext)
    }

    @Test
    fun `emits initial state when first collected`() = runBlockingTest {
        val emissions = mutableListOf<SearchViewState>()
        val job = launch {
            viewModel.searchViewState.toList(emissions)
        }
        assertThat(emissions).containsExactly(Initial)
        job.cancel()
    }

    @Test
    fun `emits all view states when search is invoked`() = runBlockingTest {
        val emissions = mutableListOf<SearchViewState>()
        mockkStatic("com.ngmatt.weedmapsandroidcodechallenge.utils.ExtensionsKt")
        val location = mock<Location>()
        val businessList = listOf(Business(id = "0"), Business(id = "1"), Business(id = "2"))
        val reviewsList = listOf(Review(id = "0", 2), Review(id = "1", 2), Review(id = "2", 2))
        coEvery { mockContext.awaitLastLocation() } returns location
        coEvery {
            mockBusinessRepo.search(
                ofType(),
                ofType(),
                ofType(),
                ofType()
            )
        } returns businessList
        coEvery { mockBusinessRepo.getBusinessReviews(ofType()) } returns reviewsList
        val job = launch {
            viewModel.searchViewState.toList(emissions)
        }
        viewModel.search("walmart")
        assertThat(emissions).hasSize(3)
        assertThat(emissions).contains(Loading)
        assertThat(emissions).contains(Initial)
        assertThat(emissions.last()).isInstanceOf(SearchLoaded::class.java)
        job.cancel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
