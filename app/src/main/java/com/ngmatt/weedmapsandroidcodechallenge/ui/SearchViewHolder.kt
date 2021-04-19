package com.ngmatt.weedmapsandroidcodechallenge.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ngmatt.weedmapsandroidcodechallenge.databinding.SearchItemBinding

class SearchViewHolder(
    private val binding: SearchItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(search: SearchAndReview) {
        binding.apply {
            val (business, topReview) = search
            tvName.text = business.name
            tvRating.text = business.rating?.toString()
            tvTopReview.text = topReview?.text

            val progressDrawable = CircularProgressDrawable(binding.root.context)
            progressDrawable.strokeWidth = 5f
            progressDrawable.centerRadius = 30f
            progressDrawable.start()

            val options = RequestOptions()
            options.centerCrop()
            options.placeholder(progressDrawable)

            Glide.with(binding.root.context)
                .load(business.imageUrl)
                .apply(options)
                .into(ivBusiness)
        }
    }
}
