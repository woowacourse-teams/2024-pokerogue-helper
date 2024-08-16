package poke.rogue.helper.presentation.biome.detail.gym

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemBiomeGymBinding
import poke.rogue.helper.presentation.biome.model.BiomePokemonUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class BiomeGymAdapter : ListAdapter<BiomePokemonUiModel, BiomeGymViewHolder>(gymPokemonComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiomeGymViewHolder {
        return BiomeGymViewHolder(
            ItemBiomeGymBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: BiomeGymViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val gymPokemonComparator =
            ItemDiffCallback<BiomePokemonUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem == newItem },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
