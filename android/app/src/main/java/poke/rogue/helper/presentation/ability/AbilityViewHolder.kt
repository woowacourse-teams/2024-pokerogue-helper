package poke.rogue.helper.presentation.ability

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemAbilityDescriptionBinding

class AbilityViewHolder(private val binding: ItemAbilityDescriptionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(abilityUiModel: AbilityUiModel) {
        binding.ability = abilityUiModel
        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context, "특성 상세페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
