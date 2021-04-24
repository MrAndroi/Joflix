package com.shorman.movies.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import com.shorman.movies.api.models.others.Language
import kotlinx.android.synthetic.main.language_item.view.*

class LanguagesAdapter(private val c: Context, @LayoutRes private val layoutResource: Int, private val movies: Array<Language>) :
    ArrayAdapter<Language>(c, layoutResource, movies) {

    var languages: List<Language> = movies.toList()

    override fun getCount(): Int = languages.size

    override fun getItem(position: Int): Language = languages[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(c).inflate(layoutResource, parent, false)

        view.tvLanguageName.text = languages[position].name
        view.tvLanguageCode.text = languages[position].code

        return view
    }

}