package poke.rogue.helper.presentation.dex

import android.content.Context
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import androidx.core.view.setMargins
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.view.TypeChip

class PokemonTypesAdapter(private val context: Context, private val viewGroup: ViewGroup) {
    fun addTypes(
        types: List<TypeUiModel>,
        config: TypeChip.PokemonTypeViewConfiguration,
    ) {
        viewGroup.removeAllViews()

        types.forEach { type ->
            val typeChip =
                TypeChip(context).apply {
                    layoutParams =
                        LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f,
                        ).apply {
                            setMargins(horizontalMargin = config.spacingBetweenTypes)
                        }
                    TypeChip.setTypeUiConfiguration(
                        view = this,
                        typeUiModel = type,
                        typeViewConfiguration = config,
                    )
                }
            viewGroup.addView(typeChip)
        }
    }
}

private fun MarginLayoutParams.setMargins(
    topMargin: Int = 0,
    bottomMargin: Int = 0,
    horizontalMargin: Int,
) {
    setMargins(horizontalMargin, topMargin, horizontalMargin, bottomMargin)
}
