package poke.rogue.helper.presentation.biome

import android.content.Context
import android.widget.ImageView
import com.google.android.flexbox.FlexboxLayout
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.util.view.dp

class BiomeTypesAdapter(private val context: Context, private val viewGroup: FlexboxLayout) {

    fun addTypes(
        types: List<TypeUiModel>,
        spacingBetweenTypes: Int = 0.dp,
        iconSize: Int = 18.dp,
    ) {
        viewGroup.removeAllViews()

        types.forEach { type ->
            val imageView = ImageView(context).apply {
                setImageResource(type.typeIconResId)

                layoutParams = FlexboxLayout.LayoutParams(
                    iconSize,
                    iconSize
                ).apply {
                    setMargins(
                        spacingBetweenTypes,
                        0,
                        0,
                        0,
                    )
                }
            }

            viewGroup.addView(imageView)
        }
    }
}
