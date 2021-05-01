package com.shorman.movies.ui.movie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shorman.movies.R
import com.shorman.movies.api.models.movie.MovieModel
import com.shorman.movies.others.Constans.IMAGES_BASE_URL
import kotlinx.android.synthetic.main.saved_movie_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedMoviesAdapter(
    private val onClick:(movieID:Int) -> Unit,private val unSaveMovie:(movie:MovieModel) -> Unit
) : RecyclerView.Adapter<SavedMoviesAdapter.SavedMoviesViewHolder>() {

    class SavedMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private val diffUtil = object : DiffUtil.ItemCallback<MovieModel>(){
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedMoviesViewHolder {
        return SavedMoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.saved_movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SavedMoviesViewHolder, position: Int) {
        val movie = differ.currentList[position]

        holder.itemView.apply {
            savedMovieImage.load(IMAGES_BASE_URL+movie.poster_path){
                crossfade(true)
                crossfade(500)
                placeholder(R.drawable.loading)
                error(R.drawable.place_holder)
            }
            savedMovieName.text = movie.original_title
            savedMovieOverView.text = movie.overview
            savedMovieDate.text = movie.release_date
            savedMovieRate.text = movie.vote_average.toString()
            btnRemoveMovie.setOnClickListener {
                unSaveMovie(movie)
            }
            setOnClickListener{
                onClick(movie.id)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}