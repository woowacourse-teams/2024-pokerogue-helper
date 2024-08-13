package poke.rogue.helper.presentation.dex.detail

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonDetailMovesBinding
import poke.rogue.helper.presentation.dex.model.PokemonMoveUiModel

class PokemonMoveViewHolder(
    private val binding: ItemPokemonDetailMovesBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(move: PokemonMoveUiModel) {
        binding.move = move
    }
}
