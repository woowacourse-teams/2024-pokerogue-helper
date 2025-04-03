package poke.rogue.helper.presentation.dex.detail.skill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonDetailSkillBinding
import poke.rogue.helper.databinding.ItemPokemonDetailSkillHeaderBinding
import poke.rogue.helper.databinding.ItemPokemonDetailSkillSectionTitleBinding
import poke.rogue.helper.presentation.dex.model.SkillListItem

class PokemonDetailSkillAdapter :
    ListAdapter<SkillListItem, RecyclerView.ViewHolder>(diffCallback) {
    override fun getItemViewType(position: Int): Int = getItem(position).viewType

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        LayoutInflater.from(parent.context).let { inflater ->
            when (viewType) {
                SkillListItem.VIEW_TYPE_SECTION_TITLE ->
                    SectionTitleViewHolder(
                        ItemPokemonDetailSkillSectionTitleBinding.inflate(inflater, parent, false),
                    )

                SkillListItem.VIEW_TYPE_HEADER ->
                    HeaderViewHolder(
                        ItemPokemonDetailSkillHeaderBinding.inflate(inflater, parent, false),
                    )

                SkillListItem.VIEW_TYPE_SKILL ->
                    SkillViewHolder(
                        ItemPokemonDetailSkillBinding.inflate(inflater, parent, false),
                    )

                else -> error("Invalid viewType: $viewType")
            }
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = getItem(position)) {
            is SkillListItem.SectionTitle -> (holder as SectionTitleViewHolder).bind(item.title)
            is SkillListItem.Header -> Unit
            is SkillListItem.Skill -> (holder as SkillViewHolder).bind(item)
        }
    }

    class SectionTitleViewHolder(private val binding: ItemPokemonDetailSkillSectionTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String) {
            binding.title = title
        }
    }

    class HeaderViewHolder(binding: ItemPokemonDetailSkillHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    class SkillViewHolder(private val binding: ItemPokemonDetailSkillBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: SkillListItem.Skill) {
            binding.skill = model
        }
    }

    companion object {
        private val diffCallback =
            object : DiffUtil.ItemCallback<SkillListItem>() {
                override fun areItemsTheSame(
                    oldItem: SkillListItem,
                    newItem: SkillListItem,
                ): Boolean = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: SkillListItem,
                    newItem: SkillListItem,
                ): Boolean = oldItem == newItem
            }
    }
}
