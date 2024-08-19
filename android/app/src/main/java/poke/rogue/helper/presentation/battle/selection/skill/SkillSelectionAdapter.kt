package poke.rogue.helper.presentation.battle.selection.skill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemBattleSkillSelectionBinding
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.battle.selection.BattleSelectionHandler
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class SkillSelectionAdapter(private val selectionHandler: BattleSelectionHandler) :
    ListAdapter<SkillSelectionUiModel, SkillSelectionViewHolder>(skillComparator) {
    private var selectedSkillId: String? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SkillSelectionViewHolder =
        SkillSelectionViewHolder(
            ItemBattleSkillSelectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            selectionHandler,
        )

    override fun onBindViewHolder(
        viewHolder: SkillSelectionViewHolder,
        position: Int,
    ) {
        val skill = getItem(position)
        val isSelected = skill.id == selectedSkillId
        viewHolder.bind(skill, isSelected)
    }

    fun updateSelectedSkill(selectedId: String) {
        var previousSelectedPosition: Int? = null
        var newSelectedPosition: Int? = null

        currentList.forEachIndexed { index, skill ->
            if (skill.id == selectedSkillId) {
                previousSelectedPosition = index
            }
            if (skill.id == selectedId) {
                newSelectedPosition = index
            }
            if (previousSelectedPosition != null && newSelectedPosition != null) {
                return@forEachIndexed
            }
        }

        selectedSkillId = selectedId
        previousSelectedPosition?.let { notifyItemChanged(it) }
        newSelectedPosition?.let { notifyItemChanged(it) }
    }

    companion object {
        private val skillComparator =
            ItemDiffCallback<SkillSelectionUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
