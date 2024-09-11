package poke.rogue.helper.presentation.battle.selection.skill

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemBattleSkillSelectionBinding
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel

class SkillSelectionViewHolder(
    private val binding: ItemBattleSkillSelectionBinding,
    private val selectionHandler: SkillSelectionHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        skillSelectionUiModel: SkillSelectionUiModel,
        isSelected: Boolean,
    ) {
        binding.skill = skillSelectionUiModel
        binding.isSelected = isSelected
        binding.selectionHandler = selectionHandler
    }
}
