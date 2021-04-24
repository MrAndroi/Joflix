package com.shorman.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shorman.movies.Constans.IMAGES_BASE_URL
import com.shorman.movies.R
import com.shorman.movies.moviesApi.models.movie.Backdrop
import kotlinx.android.synthetic.main.movie_image_item.view.*

class MovieImageAdapter() : RecyclerView.Adapter<MovieImageAdapter.MovieImageViewHolder>() {

    class MovieImageViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView)


    private val diffUtil = object :DiffUtil.ItemCallback<Backdrop>(){
        override fun areItemsTheSame(oldItem: Backdrop, newItem: Backdrop): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Backdrop, newItem: Backdrop): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieImageViewHolder {
        return MovieImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_image_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieImageViewHolder, position: Int) {
        val image = differ.currentList[position]

        holder.itemView.apply{
            movieImageDetails.load(IMAGES_BASE_URL+image.file_path){
                crossfade(true)
                crossfade(500)
                placeholder(R.drawable.loading)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}