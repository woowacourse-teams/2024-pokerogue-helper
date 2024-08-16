package poke.rogue.helper.presentation.battle.selection.skill

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemBattleSkillSelectionBinding
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel

class SkillSelectionViewHolder(private val binding: ItemBattleSkillSelectionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(skillSelectionUiModel: SkillSelectionUiModel) {
        binding.skill = skillSelectionUiModel
    }
}
