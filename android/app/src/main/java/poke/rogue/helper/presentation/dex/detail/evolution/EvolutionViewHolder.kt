package poke.rogue.helper.presentation.dex.detail.evolution

import android.view.View.GONE
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ItemPokemonDetailEvolutionBinding
import poke.rogue.helper.presentation.dex.detail.evolution.EvolutionViewHolder.Companion.level
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel
import poke.rogue.helper.presentation.dex.model.SingleEvolutionUiModel.Companion.LEVEL_DOES_NOT_MATTER
import poke.rogue.helper.presentation.util.context.stringOf

class EvolutionViewHolder(
    private val binding: ItemPokemonDetailEvolutionBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemonEvolutionUiModel: SingleEvolutionUiModel) {
        binding.evolution = pokemonEvolutionUiModel
    }

    companion object {
        @JvmStatic
        @BindingAdapter("evolutionLevel")
        fun TextView.level(level: Int) {
            if (level == LEVEL_DOES_NOT_MATTER) {
                visibility = GONE
                return
            }
            text = context.stringOf(resId = R.string.pokemon_detail_evolution_level, level)
        }

        @JvmStatic
        @BindingAdapter("item")
        fun TextView.item(item: String?) {
            if (item == null || item.contains("EMPTY") || item.contains("none") || item.isBlank()) {
                visibility = GONE
                return
            }
            text = item
        }

        @JvmStatic
        @BindingAdapter("condition")
        fun TextView.condition(condition: String?) {
            if (condition == null || condition.contains("EMPTY") || condition.contains("none") || condition.isBlank()) {
                visibility = GONE
                return
            }
            text = condition
        }
    }
}
