package poke.rogue.helper.presentation.dex.detail.skill

import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemPokemonDetailSkillBinding
import poke.rogue.helper.presentation.dex.model.SkillListItem

class PokemonDetailSkillViewHolder(
    private val binding: ItemPokemonDetailSkillBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(skill: SkillListItem.Skill) {
        binding.skill = skill
    }
}
