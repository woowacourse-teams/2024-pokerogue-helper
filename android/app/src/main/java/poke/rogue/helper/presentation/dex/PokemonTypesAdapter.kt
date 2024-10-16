package poke.rogue.helper.presentation.dex

import android.content.Context
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.view.dp

class PokemonTypesAdapter(private val context: Context, private val viewGroup: ViewGroup) {
    fun addTypes(
        types: List<TypeUiModel>,
        config: TypeChip.PokemonTypeViewConfiguration,
        spacingBetweenTypes: Int = 0.dp,
    ) {
        viewGroup.removeAllViews()

        types.forEach { type ->
            val typeChip =
                TypeChip(context).apply {
                    layoutParams =
                        LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f,
                        ).apply {
                            setMargins(horizontalMargin = spacingBetweenTypes)
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

    private fun MarginLayoutParams.setMargins(
        topMargin: Int = 0.dp,
        bottomMargin: Int = 0.dp,
        horizontalMargin: Int,
    ) {
        setMargins(horizontalMargin, topMargin, horizontalMargin, bottomMargin)
    }
}
