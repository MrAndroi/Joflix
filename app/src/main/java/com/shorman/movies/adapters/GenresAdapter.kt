package com.shorman.movies.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import com.shorman.movies.api.models.others.Genre
import kotlinx.android.synthetic.main.genre_item.view.*

class GenresAdapter(private val c: Context, @LayoutRes private val layoutResource: Int, private val genres: Array<Genre>) :
    ArrayAdapter<Genre>(c, layoutResource, genres) {

    var genresList: List<Genre> = genres.toList()

    override fun getCount(): Int = genresList.size

    override fun getItem(position: Int): Genre = genresList[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(c).inflate(layoutResource, parent, false)

        view.tvGenreName.text = genresList[position].name

        return view
    }

}