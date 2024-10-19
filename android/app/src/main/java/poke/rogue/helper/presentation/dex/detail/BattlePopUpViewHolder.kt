package poke.rogue.helper.presentation.dex.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import poke.rogue.helper.databinding.ItemPokemonDetailToBattlePopUpBinding

class BattlePopUpViewHolder(
    private val binding: ItemPokemonDetailToBattlePopUpBinding,
    private val battlePopUpHandler: BattlePopUpHandler,
) {
    private val itemView: View = binding.root

    fun bind(item: BattlePopUpUiModel): View {
        with(binding) {
            battlePopUp = item
            battlePopUpHandler = this@BattlePopUpViewHolder.battlePopUpHandler
        }

        return itemView
    }

    companion object {
        fun inflated(
            parent: ViewGroup,
            battlePopUpHandler: BattlePopUpHandler,
        ): BattlePopUpViewHolder =
            BattlePopUpViewHolder(
                binding =
                    ItemPokemonDetailToBattlePopUpBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                battlePopUpHandler = battlePopUpHandler,
            )
    }
}

interface BattlePopUpHandler {
    fun navigateToBattle(battlePopUpUiModel: BattlePopUpUiModel)
}
