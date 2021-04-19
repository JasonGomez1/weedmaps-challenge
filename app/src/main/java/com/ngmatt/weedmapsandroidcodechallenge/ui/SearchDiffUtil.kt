package com.ngmatt.weedmapsandroidcodechallenge.ui

import androidx.recyclerview.widget.DiffUtil

class SearchDiffUtil : DiffUtil.ItemCallback<SearchAndTopReview>() {
    override fun areItemsTheSame(oldItem: SearchAndTopReview, newItem: SearchAndTopReview) =
        oldItem.business.id == newItem.business.id

    override fun areContentsTheSame(oldItem: SearchAndTopReview, newItem: SearchAndTopReview) =
        oldItem == newItem
}
