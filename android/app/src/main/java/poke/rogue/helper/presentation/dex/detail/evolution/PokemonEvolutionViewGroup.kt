package poke.rogue.helper.presentation.dex.detail.evolution

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import poke.rogue.helper.databinding.ViewGroupPokemonEvolutionBinding

class PokemonEvolutionViewGroup
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
    ) : ConstraintLayout(context, attrs, defStyle) {
        val recyclerView: RecyclerView
        private val binding = ViewGroupPokemonEvolutionBinding.inflate(LayoutInflater.from(context), this, true)

        init {
            recyclerView = binding.recyclerView
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }
