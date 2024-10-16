package poke.rogue.helper.presentation.dex.detail.evolution

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import poke.rogue.helper.databinding.ViewGroupPokemonEvolutionBinding
import poke.rogue.helper.presentation.dex.detail.PokemonDetailNavigateHandler
import poke.rogue.helper.presentation.dex.model.EvolutionsUiModel
import timber.log.Timber

class OuterEvolutionViewHolder(
    private val binding: ViewGroupPokemonEvolutionBinding,
    private val navigateHandler: PokemonDetailNavigateHandler,
) : ViewHolder(binding.root) {
    private val evolutionAdapter by lazy { EvolutionAdapter(navigateHandler) }

    fun bind(evolutionsUiModel: EvolutionsUiModel) {
        Timber.d("evolutionsUiModel: $evolutionsUiModel")
        binding.recyclerView.adapter = evolutionAdapter
        evolutionsUiModel.evolutions.let(evolutionAdapter::submitList)
    }

    companion object {
        fun inflated(parent: ViewGroup, navigateHandler: PokemonDetailNavigateHandler) = OuterEvolutionViewHolder(
            binding = ViewGroupPokemonEvolutionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            navigateHandler = navigateHandler,
        )
    }
}