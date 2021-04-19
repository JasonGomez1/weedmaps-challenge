package com.ngmatt.weedmapsandroidcodechallenge.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ngmatt.weedmapsandroidcodechallenge.R
import com.ngmatt.weedmapsandroidcodechallenge.data.Error
import com.ngmatt.weedmapsandroidcodechallenge.data.Initial
import com.ngmatt.weedmapsandroidcodechallenge.data.Loading
import com.ngmatt.weedmapsandroidcodechallenge.data.SearchLoaded
import com.ngmatt.weedmapsandroidcodechallenge.databinding.ActivityMainBinding
import com.ngmatt.weedmapsandroidcodechallenge.utils.EndlessRecyclerViewScrollListener
import com.ngmatt.weedmapsandroidcodechallenge.utils.checkLocationPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.flow.collect


/**
 * Created by Matt Ng on 9/14/20
 */
@AndroidEntryPoint
class HelloWorldActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchAdapter = SearchAdapter()
        binding.recyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(this@HelloWorldActivity).also {
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL
                    ).apply {
                        setDrawable(ColorDrawable(getColor(android.R.color.transparent)))
                    }
                )
            }
            layoutManager = linearLayoutManager
            adapter = searchAdapter
            addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.search(binding.editText.text.toString(), page * 20)
                }

            })
        }
        checkLocationPermission {
            lifecycleScope.launchWhenStarted {
                viewModel.searchViewState.collect { viewState ->
                    when (viewState) {
                        Error -> {
                            binding.apply {
                                progressBar.hide()
                                progressBar.isVisible = false
                                recyclerView.isVisible = false
                                tvBanner.isVisible = false
                                tvFailedSearch.isVisible = true
                            }

                        }
                        Loading -> {
                            binding.apply {
                                tvFailedSearch.isVisible = false
                                progressBar.isVisible = true
                                tvBanner.isVisible = false
                                progressBar.show()
                            }
                        }
                        is SearchLoaded -> {
                            binding.apply {
                                progressBar.hide()
                                progressBar.isVisible = false
                                tvBanner.isVisible = true
                                recyclerView.isVisible = true
                            }
                            searchAdapter.submitList(searchAdapter.currentList.toMutableList().let {
                                it.addAll(viewState.searchResult)
                                it
                            })
                        }
                        Initial -> {
                            binding.recyclerView.isVisible = false
                        }
                    }

                }
            }

        }
        binding.editText.setOnEditorActionListener { editText, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.search(editText.text.toString())
                binding.tvBanner.text = getString(R.string.search_banner, editText.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}