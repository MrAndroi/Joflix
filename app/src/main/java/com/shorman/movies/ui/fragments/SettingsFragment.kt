package com.shorman.movies.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.shorman.movies.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.change_language_dialog.view.*
import kotlinx.android.synthetic.main.settings_fragment.*

@AndroidEntryPoint
class SettingsFragment:Fragment(R.layout.settings_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeLanguage.setOnClickListener {
            showChangeLanguageDialog()
        }

    }

    @SuppressLint("InflateParams")
    private fun showChangeLanguageDialog() {
        val dialog = MaterialAlertDialogBuilder(requireContext()).create()
        val layout = layoutInflater.inflate(R.layout.change_language_dialog, null)
        layout.languageButtons.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.arabicLanguage -> {
                    changeLanguage("ar")
                }
                R.id.englishLanguage -> {
                    changeLanguage("en")
                }
            }
        }
        layout.tvCancelLanguageDialog.setOnClickListener { dialog.dismiss() }
        dialog.setView(layout)
        dialog.show()

    }

    private fun changeLanguage(lang: String) {
        val editor = activity?.getSharedPreferences("language", Context.MODE_PRIVATE)?.edit()
        editor?.putString("lang", lang)
        editor?.apply()
        activity?.finish()
        startActivity(activity?.intent)
        activity?.overridePendingTransition(0, 0)
    }
}