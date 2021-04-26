package com.shorman.movies.ui.movie.adapters

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
import com.shorman.movies.api.models.movie.MovieModel
import com.shorman.movies.others.Constans.IMAGES_BASE_URL
import kotlinx.android.synthetic.main.show_item.view.*

class MoviesAdapter(private val onClick:(movieId:Int) -> Unit)
    :PagingDataAdapter<MovieModel, MoviesAdapter.ShowsViewHolder>(MOVIE_COMPARER) {

    class ShowsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder {
       return ShowsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.show_item,parent,false))
    }

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
        val show = getItem(position)

        holder.itemView.apply{
            show?.let {movieModel ->
                showImage.load(IMAGES_BASE_URL+movieModel.poster_path){
                    placeholder(R.drawable.place_holder)
                    error(R.drawable.place_holder)
                    crossfade(true)
                    crossfade(1000)
                }
                tvShowName.text = movieModel.original_title
                tvShowYear.text = movieModel.release_date
                setOnClickListener {
                    onClick(movieModel.id)
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
         val MOVIE_COMPARER = object :DiffUtil.ItemCallback<MovieModel>(){

            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}