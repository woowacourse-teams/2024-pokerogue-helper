package poke.rogue.helper.presentation.type.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.Dimension
import androidx.core.content.res.use
import androidx.databinding.BindingAdapter
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ItemTypeBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.px

class TypeChip
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : LinearLayout(context, attrs, defStyleAttr) {
        private val binding: ItemTypeBinding =
            ItemTypeBinding.inflate(LayoutInflater.from(context), this, true)

        init {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.TypeChip)
            attributes.use {
                val iconSize = attributes.getDimension(R.styleable.TypeChip_iconSize, 18f)
                val spacing = attributes.getDimension(R.styleable.TypeChip_spacing, 7f)
                val nameSize = attributes.getDimension(R.styleable.TypeChip_nameSize, 8f).px
                initViews(iconSize, spacing, nameSize)
            }
        }

        private fun initViews(
            @Dimension iconSize: Float,
            @Dimension spacing: Float,
            @Dimension nameSize: Float,
        ) = with(binding) {
            orientation = HORIZONTAL
            ivTypeNameIcon.layoutParams.apply {
                width = iconSize.toInt()
                height = iconSize.toInt()
            }
            spaceType.layoutParams.width = spacing.toInt()
            tvTypeName.textSize = nameSize
        }

        companion object {
            @JvmStatic
            @BindingAdapter("type", "PokemonTypeViewConfiguration")
            fun setTypeUiConfiguration(
                view: TypeChip,
                typeUiModel: TypeUiModel,
                typeViewConfiguration: PokemonTypeViewConfiguration,
            ) {
                with(view.binding) {
                    type = typeUiModel
                    viewConfiguration = typeViewConfiguration
                }
            }

            @JvmStatic
            @BindingAdapter("layoutWidth")
            fun setLayoutWidth(
                view: View,
                width: Int,
            ) {
                view.layoutParams.width = width
            }

            @JvmStatic
            @BindingAdapter("layoutHeight")
            fun setLayoutHeight(
                view: View,
                height: Int,
            ) {
                view.layoutParams.height = height
            }
        }

        data class PokemonTypeViewConfiguration(
            val nameSize: Int = 8.dp,
            val iconSize: Int = 18.dp,
            val layoutWidth: Int = ViewGroup.LayoutParams.MATCH_PARENT,
            val layoutGravity: Int = Gravity.CENTER,
            val spacing: Int = 7.dp,
            val hasBackGround: Boolean = false,
            val spacingBetweenTypes: Float = 8f,
        )
    }
