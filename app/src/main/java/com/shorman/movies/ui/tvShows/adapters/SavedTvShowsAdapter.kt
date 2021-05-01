package com.shorman.movies.ui.tvShows.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shorman.movies.R
import com.shorman.movies.api.models.movie.MovieModel
import com.shorman.movies.api.models.tvshows.TvShowModel
import com.shorman.movies.others.Constans
import com.shorman.movies.others.Constans.IMAGES_BASE_URL
import kotlinx.android.synthetic.main.saved_movie_item.view.*
import kotlinx.android.synthetic.main.saved_tv_show_item.view.*

class SavedTvShowsAdapter(
    private val onClick:(showID:Int) -> Unit,private val unSaveTvShow:(tvShow: TvShowModel) -> Unit
) : RecyclerView.Adapter<SavedTvShowsAdapter.SavedTvShowsViewHolder>() {

    class SavedTvShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private val diffUtil = object : DiffUtil.ItemCallback<TvShowModel>(){
        override fun areItemsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedTvShowsViewHolder {
        return SavedTvShowsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.saved_tv_show_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SavedTvShowsViewHolder, position: Int) {
        val tvShow = differ.currentList[position]

        holder.itemView.apply {
            savedTvShowImage.load(IMAGES_BASE_URL+tvShow.poster_path){
                placeholder(R.drawable.loading)
                error(R.drawable.place_holder)
                crossfade(true)
                crossfade(500)
            }
            savedTvShowName.text = tvShow.original_name
            savedTvShowDate.text = tvShow.first_air_date
            savedTvShowOverView.text = tvShow.overview
            savedTvShowRate.text = tvShow.vote_average.toString()
            setOnClickListener{
                onClick(tvShow.id)
            }
            btnRemoveTvShow.setOnClickListener {
                unSaveTvShow(tvShow)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}