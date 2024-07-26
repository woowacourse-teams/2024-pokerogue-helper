package poke.rogue.helper.presentation.util.view

import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter

@BindingAdapter("onQueryTextChange")
fun setOnQueryTextListener(
    searchView: SearchView,
    onQueryTextChangeListener: QueryHandler,
) {
    searchView.setOnQueryTextListener(
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                onQueryTextChangeListener.onQueryName(newText.toString())
                return true
            }
        },
    )
}
