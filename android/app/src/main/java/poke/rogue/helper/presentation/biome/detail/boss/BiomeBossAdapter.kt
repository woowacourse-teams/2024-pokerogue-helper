package poke.rogue.helper.presentation.biome.detail.boss

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemBiomePokemonBinding
import poke.rogue.helper.presentation.biome.model.BiomePokemonUiModel
import poke.rogue.helper.presentation.dex.PokemonListNavigateHandler
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class BiomeBossAdapter(
    private val onClickPokemon: PokemonListNavigateHandler,
) :
    ListAdapter<BiomePokemonUiModel, BiomeBossViewHolder>(wildPokemonComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BiomeBossViewHolder {
        return BiomeBossViewHolder(
            ItemBiomePokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            onClickPokemon,
        )
    }

    override fun onBindViewHolder(
        holder: BiomeBossViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        val wildPokemonComparator =
            ItemDiffCallback<BiomePokemonUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem == newItem },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
