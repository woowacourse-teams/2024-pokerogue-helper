package poke.rogue.helper.presentation.dex.detail.skill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemPokemonDetailMovesBinding
import poke.rogue.helper.presentation.dex.model.PokemonMoveUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class PokemonDetailSkillAdapter : ListAdapter<PokemonMoveUiModel, PokemonDetailSkillViewHolder>(moveComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PokemonDetailSkillViewHolder =
        PokemonDetailSkillViewHolder(
            ItemPokemonDetailMovesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(
        holder: PokemonDetailSkillViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        private val moveComparator =
            ItemDiffCallback<PokemonMoveUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
