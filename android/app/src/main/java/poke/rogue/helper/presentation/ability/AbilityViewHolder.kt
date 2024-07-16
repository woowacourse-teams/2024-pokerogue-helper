package poke.rogue.helper.presentation.ability

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemAbilityDescriptionBinding
import poke.rogue.helper.presentation.ability.detail.AbilityDetailActivity

class AbilityViewHolder(private val binding: ItemAbilityDescriptionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(abilityUiModel: AbilityUiModel) {
        binding.ability = abilityUiModel
        binding.root.setOnClickListener {
            AbilityDetailActivity.intent(binding.root.context).also(binding.root.context::startActivity)
        }
    }
}
