package poke.rogue.helper.presentation.ability

import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import poke.rogue.helper.presentation.biome.BiomeQueryHandler

@BindingAdapter("onQueryTextChange")
fun setOnQueryTextListener(
    searchView: SearchView,
    onQueryTextChangeListener: BiomeQueryHandler,
) {
    searchView.setOnQueryTextListener(
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                onQueryTextChangeListener.queryName(newText.toString())
                return true
            }
        },
    )
}