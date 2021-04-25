package com.shorman.movies.ui.tvShows.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shorman.movies.R
import com.shorman.movies.api.models.tvshows.Season
import com.shorman.movies.others.Constans
import com.shorman.movies.others.Constans.IMAGES_BASE_URL
import kotlinx.android.synthetic.main.movie_image_item.view.*
import kotlinx.android.synthetic.main.season_item.view.*

class SeasonAdapter: RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder>() {

    class SeasonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private val diffUtil = object : DiffUtil.ItemCallback<Season>(){
        override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        return SeasonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.season_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val season = differ.currentList[position]

        holder.itemView.apply{
            seasonImage.load(IMAGES_BASE_URL+season.poster_path){
                placeholder(R.drawable.loading)
                error(R.drawable.place_holder)
                crossfade(true)
                crossfade(500)
            }
            seasonNumber.text = season.season_number.toString()
            tvSeasonName.text = season.name
            if(season.overview == ""){
                tvSeasonOverView.text = "There is no overview available for this season :("
            }
            else{
                tvSeasonOverView.text = season.overview
            }
            tvSeasonDate.text = season.air_date
            tvSeasonEpisodes.text = "${season.episode_count} Episodes"
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}