package poke.rogue.helper.presentation.type.type_selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemTypeChoiceBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel

class TypeSelectionAdapter(private val types: List<TypeUiModel> = listOf()) :
    RecyclerView.Adapter<TypeSelectionViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TypeSelectionViewHolder {
        val view = ItemTypeChoiceBinding.inflate(LayoutInflater.from(parent.context))
        return TypeSelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return types.size
    }

    override fun onBindViewHolder(
        holder: TypeSelectionViewHolder,
        position: Int,
    ) {
        val item = types[position]
        holder.bind(item)
    }
}
