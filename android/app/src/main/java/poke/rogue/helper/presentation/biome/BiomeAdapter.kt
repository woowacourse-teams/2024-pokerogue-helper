package poke.rogue.helper.presentation.biome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemBiomeBinding
import poke.rogue.helper.presentation.biome.model.BiomeUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class BiomeAdapter(/*private val onClickBiomeItem: BiomeUiEventHandler*/) :
    ListAdapter<BiomeUiModel, BiomeViewHolder>(biomeComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BiomeViewHolder {
        return BiomeViewHolder(
            ItemBiomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
           // onClickBiomeItem,
        )
    }

    override fun onBindViewHolder(
        holder: BiomeViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        val biomeComparator =
            ItemDiffCallback<BiomeUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
