package poke.rogue.helper.presentation.dex.detail

import android.content.Context
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.view.PokemonTypeViewConfiguration
import poke.rogue.helper.presentation.type.view.TypeChip

class PokemonTypesAdapter(private val context: Context, private val viewGroup: ViewGroup) {
    fun addTypes(
        types: List<TypeUiModel>,
        config: PokemonTypeViewConfiguration,
    ) {
        types.forEach { type ->
            val typeChip =
                TypeChip(context).apply {
                    layoutParams =
                        LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f,
                        ).apply {
                            setMargins(restMargin = config.marginBetweenTypes, bottomMargin = 0)
                        }
                    TypeChip.setTypeName(this, type, config.isEmptyBackground, config.nameSize, config.iconSize)
                }
            viewGroup.addView(typeChip)
        }
    }
}

private fun MarginLayoutParams.setMargins(
    bottomMargin: Int,
    restMargin: Int,
) {
    setMargins(restMargin, restMargin, restMargin, bottomMargin)
}
