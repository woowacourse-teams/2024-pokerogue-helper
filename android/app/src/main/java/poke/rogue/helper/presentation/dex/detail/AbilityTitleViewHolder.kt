package poke.rogue.helper.presentation.dex.detail

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemAbilityTitleBinding
import poke.rogue.helper.presentation.ability.AbilityUiEventHandler
import poke.rogue.helper.presentation.dex.model.AbilityTitleUiModel

class AbilityTitleViewHolder(
    private val binding: ItemAbilityTitleBinding,
    private val onClickAbility: AbilityUiEventHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ability: AbilityTitleUiModel) {
        with(binding) {
            tvAbilityTitle.text = ability.name
            tvAbilityTitle.setOnClickListener {
                onClickAbility
            }
        }
    }
}
