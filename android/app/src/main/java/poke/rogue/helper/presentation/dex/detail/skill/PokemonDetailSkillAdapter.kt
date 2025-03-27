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
    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is SkillListItem.SectionTitle -> VIEW_TYPE_SECTION_TITLE
            is SkillListItem.Header -> VIEW_TYPE_HEADER
            is SkillListItem.Skill -> VIEW_TYPE_SKILL
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_SECTION_TITLE -> {
                val binding =
                    ItemPokemonDetailSkillSectionTitleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                SectionTitleViewHolder(binding)
            }

            VIEW_TYPE_HEADER -> {
                val binding =
                    ItemPokemonDetailSkillHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                HeaderViewHolder(binding)
            }

            VIEW_TYPE_SKILL -> {
                val binding =
                    ItemPokemonDetailSkillBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                SkillViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid viewType")
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
        private const val VIEW_TYPE_SECTION_TITLE = 0
        private const val VIEW_TYPE_HEADER = 1
        private const val VIEW_TYPE_SKILL = 2

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
