package poke.rogue.helper.presentation.battle.selection.skill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemBattleSkillSelectionBinding
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.dex.filter.SelectableUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class SkillSelectionAdapter(private val selectionHandler: SkillSelectionHandler) :
    ListAdapter<SelectableUiModel<SkillSelectionUiModel>, SkillSelectionViewHolder>(skillComparator) {
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
        viewHolder.bind(skill.data, skill.isSelected)
    }

    companion object {
        private val skillComparator =
            ItemDiffCallback<SelectableUiModel<SkillSelectionUiModel>>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
