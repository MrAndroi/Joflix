package com.shorman.movies.ui.tvShows.adapters

import com.shorman.movies.api.models.tvshows.TvShowModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shorman.movies.others.Constans.FOOTER_VIEW_TYPE
import com.shorman.movies.others.Constans.MOVIE_VIEW_TYPE
import com.shorman.movies.R
import kotlinx.android.synthetic.main.show_item.view.*

class TvShowsAdapter(private val onClick:(tvShowID:Int) -> Unit)
    :PagingDataAdapter<TvShowModel, TvShowsAdapter.TvShowsViewHolder>(TV_SHOWS_COMPARER) {

    class TvShowsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder {
        return TvShowsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.show_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        val show = getItem(position)

        holder.itemView.apply{
            show?.let {showModel ->
                showImage.load(showModel.poster_path){
                    placeholder(R.drawable.place_holder)
                    error(R.drawable.place_holder)
                    crossfade(true)
                    crossfade(1000)
                }
                tvShowName.text = showModel.original_name
                tvShowYear.text = showModel.first_air_date
                setOnClickListener {
                    onClick(showModel.id)
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount){
            MOVIE_VIEW_TYPE
        }else {
            FOOTER_VIEW_TYPE
        }
    }

    companion object{
        val TV_SHOWS_COMPARER = object :DiffUtil.ItemCallback<TvShowModel>(){

            override fun areItemsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}