package poke.rogue.helper.presentation.type.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ItemTypeBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel

class TypeResultItemAdapter(private val types: List<TypeUiModel> = listOf()) :
    RecyclerView.Adapter<TypeResultItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TypeResultItemViewHolder {
        val view = ItemTypeBinding.inflate(LayoutInflater.from(parent.context))
        return TypeResultItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TypeResultItemViewHolder,
        position: Int,
    ) {
        val item = types[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return types.size
    }
}
