package com.ngmatt.weedmapsandroidcodechallenge.ui

import androidx.recyclerview.widget.DiffUtil

class SearchDiffUtil : DiffUtil.ItemCallback<SearchAndReview>() {
    override fun areItemsTheSame(oldItem: SearchAndReview, newItem: SearchAndReview) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: SearchAndReview, newItem: SearchAndReview) =
        oldItem == newItem
}
