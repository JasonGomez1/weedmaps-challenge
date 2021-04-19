package com.ngmatt.weedmapsandroidcodechallenge.ui

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ngmatt.weedmapsandroidcodechallenge.R
import com.ngmatt.weedmapsandroidcodechallenge.data.Error
import com.ngmatt.weedmapsandroidcodechallenge.data.Loading
import com.ngmatt.weedmapsandroidcodechallenge.data.SearchLoaded
import com.ngmatt.weedmapsandroidcodechallenge.databinding.ActivityMainBinding
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
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchAdapter = SearchAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HelloWorldActivity)
            adapter = searchAdapter
        }
        checkLocationPermission {
            lifecycleScope.launchWhenStarted {
                viewModel.searchViewState.collect { viewState ->
                    when (viewState) {
                        Error -> TODO()
                        Loading -> {
                            binding.progressBar.isVisible = true
                            binding.recyclerView.isVisible = false
                            binding.tvBanner.isVisible = false
                            binding.progressBar.show()
                        }
                        is SearchLoaded -> {
                            binding.progressBar.hide()
                            binding.progressBar.isVisible = false
                            binding.recyclerView.isVisible = true
                            binding.tvBanner.isVisible = true
                            searchAdapter.submitList(viewState.searchResult)
                        }
                        else -> { }
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