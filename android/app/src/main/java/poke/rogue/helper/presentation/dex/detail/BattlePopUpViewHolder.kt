package poke.rogue.helper.presentation.dex.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import poke.rogue.helper.databinding.ItemPokemonDetailToBattlePopUpBinding

class BattlePopUpViewHolder(private val binding: ItemPokemonDetailToBattlePopUpBinding) {
    private val itemView: View = binding.root

    fun bind(item: BattlePopUpUiModel): View {
        binding.popup = item
        return itemView
    }

    companion object {
        fun inflated(parent: ViewGroup): BattlePopUpViewHolder = BattlePopUpViewHolder(
            ItemPokemonDetailToBattlePopUpBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
