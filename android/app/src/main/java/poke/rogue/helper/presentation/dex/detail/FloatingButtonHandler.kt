package poke.rogue.helper.presentation.dex.detail

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import poke.rogue.helper.R

// TODO: use koin
class FloatingButtonHandler(
    context: Context,
    private val fab: View,
    private val withMyPokemon: View,
    private val withOpponentPokemon: View
) {
    private val rotateOpen: Animation = AnimationUtils.loadAnimation(context, R.anim.rotate_open)
    private val rotateClose: Animation = AnimationUtils.loadAnimation(context, R.anim.rotate_close)
    private val fromBottom: Animation = AnimationUtils.loadAnimation(context, R.anim.from_bottom)
    private val toBottom: Animation = AnimationUtils.loadAnimation(context, R.anim.to_bottom)

    private var isExpanded = false

    fun toggleFab() {
        if (!isExpanded) {
            withMyPokemon.visibility = View.VISIBLE
            withOpponentPokemon.visibility = View.VISIBLE

            fab.startAnimation(rotateOpen)
            withMyPokemon.startAnimation(fromBottom)
            withOpponentPokemon.startAnimation(fromBottom)
        } else {
            withMyPokemon.startAnimation(toBottom)
            withOpponentPokemon.startAnimation(toBottom)
            fab.startAnimation(rotateClose)

            withMyPokemon.visibility = View.INVISIBLE
            withOpponentPokemon.visibility = View.INVISIBLE
        }

        isExpanded = !isExpanded
    }
}
