package com.shorman.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shorman.movies.others.Constans.IMAGES_BASE_URL
import com.shorman.movies.R
import com.shorman.movies.api.models.others.Cast
import kotlinx.android.synthetic.main.actor_item.view.*

class CastAdapter(private val onClick:(actor: Cast, itemView:View) -> Unit) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    class CastViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView)


    private val diffUtil = object :DiffUtil.ItemCallback<Cast>(){
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.cast_id == newItem.cast_id
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.actor_item,parent,false))
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = differ.currentList[position]

        holder.itemView.apply{
            actorImage.load(IMAGES_BASE_URL+cast.profile_path){
                placeholder(R.drawable.actor_loading)
                error(R.drawable.actor_loading)
            }
            setOnClickListener {  onClick(cast,this)}
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}