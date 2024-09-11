package poke.rogue.helper.presentation.dex.detail.stat

import android.graphics.Typeface
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ItemAbilityTitleBinding
import poke.rogue.helper.presentation.dex.detail.PokemonDetailNavigateHandler
import poke.rogue.helper.presentation.dex.model.PokemonDetailAbilityUiModel

class AbilityTitleViewHolder(
    private val binding: ItemAbilityTitleBinding,
    private val onClickAbility: PokemonDetailNavigateHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ability: PokemonDetailAbilityUiModel) {
        binding.ability = ability
        binding.onClickAbility = onClickAbility
    }

    companion object {
        @JvmStatic
        @BindingAdapter("passive")
        fun TextView.passive(passive: Boolean) {
            if (passive) {
                setTypeface(null, Typeface.BOLD)
                setTextColor(context.getColor(R.color.poke_electric))
            } else {
                setTypeface(null, Typeface.NORMAL)
            }
        }

        @JvmStatic
        @BindingAdapter("hidden")
        fun TextView.hidden(hidden: Boolean) {
            if (hidden) {
                setTypeface(null, Typeface.BOLD)
            } else {
                setTypeface(null, Typeface.NORMAL)
            }
        }
    }
}
