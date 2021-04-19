package com.ngmatt.weedmapsandroidcodechallenge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ngmatt.weedmapsandroidcodechallenge.databinding.SearchItemBinding

class SearchAdapter : ListAdapter<SearchAndReview, SearchViewHolder>(SearchDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchViewHolder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) =
        getItem(position).let {
            holder.bind(it)
        }
}
