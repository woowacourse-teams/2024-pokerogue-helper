package poke.rogue.helper.presentation.dex.detail.information

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.data.model.Biome
import poke.rogue.helper.databinding.ItemPokemonDetailInformationBiomeBinding
import poke.rogue.helper.presentation.dex.detail.PokemonDetailNavigateHandler
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class PokemonDetailBiomeAdapter(
    private val onClickBiomeItem: PokemonDetailNavigateHandler,
) : ListAdapter<Biome, PokemonDetailBiomeViewHolder>(biomeComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PokemonDetailBiomeViewHolder =
        PokemonDetailBiomeViewHolder(
            binding =
                ItemPokemonDetailInformationBiomeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
            onClickBiomeItem = onClickBiomeItem,
        )

    override fun onBindViewHolder(
        holder: PokemonDetailBiomeViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        val biomeComparator =
            ItemDiffCallback<Biome>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
