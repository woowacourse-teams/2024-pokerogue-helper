package poke.rogue.helper.presentation.battle.selection.pokemon

import android.widget.ImageView
import com.google.android.flexbox.FlexboxLayout
import poke.rogue.helper.presentation.type.model.TypeUiModel1
import poke.rogue.helper.presentation.util.view.dp

fun FlexboxLayout.addPokemonTypes(
    types: List<TypeUiModel1>,
    spacingBetweenTypes: Int = 0.dp,
    iconSize: Int = 18.dp,
) {
    removeAllViews()

    types.forEach { type ->
        val imageView =
            ImageView(this.context).apply {
                setImageResource(type.typeIconResId)

                layoutParams =
                    FlexboxLayout.LayoutParams(iconSize, iconSize).apply {
                        setMargins(spacingBetweenTypes, 0, 0, 0)
                    }
            }
        addView(imageView)
    }
}
