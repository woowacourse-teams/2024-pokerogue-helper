package poke.rogue.helper.presentation.type.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import poke.rogue.helper.databinding.ItemTypeResultBinding
import poke.rogue.helper.presentation.type.model.MatchedTypesUiModel
import poke.rogue.helper.presentation.util.view.ItemDiffCallback

class TypeResultAdapter : ListAdapter<MatchedTypesUiModel, TypeResultViewHolder>(typeComparator) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TypeResultViewHolder =
        TypeResultViewHolder(
            ItemTypeResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(
        holder: TypeResultViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        val typeComparator =
            ItemDiffCallback<MatchedTypesUiModel>(
                onItemsTheSame = { oldItem, newItem -> oldItem.typeName == newItem.typeName },
                onContentsTheSame = { oldItem, newItem -> oldItem == newItem },
            )
    }
}
