package com.shorman.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shorman.movies.others.Constans.IMAGES_BASE_URL
import com.shorman.movies.R
import com.shorman.movies.api.models.others.Review
import kotlinx.android.synthetic.main.review_item.view.*

class CommentsAdapter()
    : PagingDataAdapter<Review, CommentsAdapter.CommentsViewHolder>(COMMENTS_COMPARER) {

    class CommentsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val review = getItem(position)

        holder.itemView.apply{
            review?.let { review ->
                commentImage.load(IMAGES_BASE_URL+review.author_details.avatar_path){
                    placeholder(R.drawable.actor_loading)
                    error(R.drawable.actor_loading)
                }
                commentUserName.text = review.author_details.username
                commentRating.text = review.author_details.rating.toString()
                tvComment.text = review.content
            }

        }
    }

    companion object{
        val COMMENTS_COMPARER = object : DiffUtil.ItemCallback<Review>(){

            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }

        }
    }
}