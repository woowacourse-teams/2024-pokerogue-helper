package poke.rogue.helper.presentation.battle.selection.skill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemBattleSkillSelectionBinding
import poke.rogue.helper.presentation.battle.model.SkillSelectionUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class SkillSelectionAdapter :
    ListAdapter<SkillSelectionUiModel, SkillSelectionViewHolder>(skillComparator) {
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
        )

    override fun onBindViewHolder(
        viewHolder: SkillSelectionViewHolder,
        position: Int,
    ) {
        viewHolder.bind(getItem(position))
    }

    companion object {
        private val skillComparator =
            ItemDiffCallback<SkillSelectionUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
