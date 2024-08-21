package poke.rogue.helper.presentation.dex.detail.stat

import android.graphics.Typeface
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ItemAbilityTitleBinding
import poke.rogue.helper.presentation.dex.detail.PokemonDetailNavigateHandler
import poke.rogue.helper.presentation.dex.model.PokemonDetailAbilityUiModel
import poke.rogue.helper.presentation.util.view.setBackgroundTint

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
        fun TextView.background(ability: PokemonDetailAbilityUiModel) {
            if (ability.passive)
                {
                    this.setBackgroundTint(R.color.poke_electric)
                }

            if (ability.hidden) {
                this.setTypeface(null, Typeface.BOLD)
            }
        }
    }
}
