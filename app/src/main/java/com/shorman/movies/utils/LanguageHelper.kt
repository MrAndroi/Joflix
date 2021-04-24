package com.shorman.movies.utils

import com.google.gson.GsonBuilder
import com.shorman.movies.api.models.others.Language

fun getAllLanguages(langInJson:String):Array<Language>{
    val langs: Array<Language> = GsonBuilder().create().fromJson(langInJson, Array<Language>::class.java)
    return langs
}