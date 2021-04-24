package com.shorman.movies.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.beforeTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            afterTextChanged.invoke(s.toString())
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {

        }
    })
}