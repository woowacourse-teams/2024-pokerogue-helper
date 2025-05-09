package poke.rogue.helper.presentation.biome.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemPokemonListPokemonBinding
import poke.rogue.helper.presentation.dex.PokemonListNavigateHandler
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class BiomPokemonAdapter(private val onClickPokemon: PokemonListNavigateHandler) :
    ListAdapter<PokemonUiModel, BiomePokemonViewHolder>(poketmonComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BiomePokemonViewHolder =
        BiomePokemonViewHolder(
            ItemPokemonListPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onClickPokemon,
        )

    override fun onBindViewHolder(
        holder: BiomePokemonViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        val poketmonComparator =
            ItemDiffCallback<PokemonUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.hashId == newItem.hashId },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
