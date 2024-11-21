package poke.rogue.helper.presentation.dex.detail.information

import android.content.Context
import android.widget.ImageView
import com.google.android.flexbox.FlexboxLayout
import poke.rogue.helper.R
import poke.rogue.helper.presentation.type.model.TypeUiModel1

class PokemonDetailBiomeTypesAdapter(private val context: Context, private val viewGroup: FlexboxLayout) {
    fun addTypes(types: List<TypeUiModel1>) {
        viewGroup.removeAllViews()

        types.forEach { type ->
            val imageView =
                ImageView(context).apply {
                    setImageResource(type.typeIconResId)

                    layoutParams =
                        FlexboxLayout.LayoutParams(
                            context.resources.getDimensionPixelSize(R.dimen.pokemon_detail_item_pokemon_biome_type_icon_size),
                            context.resources.getDimensionPixelSize(R.dimen.pokemon_detail_item_pokemon_biome_type_icon_size),
                        ).apply {
                            setMargins(
                                context.resources.getDimensionPixelSize(R.dimen.pokemon_detail_item_pokemon_biome_type_icon_spacing),
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
