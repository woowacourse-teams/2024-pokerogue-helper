package poke.rogue.helper.presentation.type.result

import android.view.Gravity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemTypeBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel1
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.view.dp

class TypeResultItemViewHolder(
    private val binding: ItemTypeBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(typeItem: TypeUiModel1) {
        binding.type = typeItem
        binding.viewConfiguration = typeViewConfiguration
    }

    companion object {
        private val typeViewConfiguration =
            TypeChip.PokemonTypeViewConfiguration(
                width = ViewGroup.LayoutParams.MATCH_PARENT,
                contentAlignment = Gravity.START,
                hasBackGround = false,
                nameSize = 14.dp,
                iconSize = 18.dp,
            )
    }
}
