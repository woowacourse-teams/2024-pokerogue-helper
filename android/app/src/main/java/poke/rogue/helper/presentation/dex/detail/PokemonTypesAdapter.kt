package poke.rogue.helper.presentation.dex.detail

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.setMargins
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.view.TypeChip

// TODO: 포켓몬 목록 화면에서도 사용할 수 있도록 파라미터들을 변경해야 함
class PokemonTypesAdapter(private val context: Context, private val viewGroup: ViewGroup) {
    fun addTypes(types: List<TypeUiModel>) {
        types.forEach { type ->
            val typeChip =
                TypeChip(context).apply {
                    layoutParams =
                        LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f,
                        ).apply {
                            setMargins(10)
                        }
                    gravity = Gravity.CENTER
                    TypeChip.setTypeName(this, type, false, 17f, 40f)
                }
            viewGroup.addView(typeChip)
        }
    }
}
