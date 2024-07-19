package poke.rogue.helper.presentation.type.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Dimension
import androidx.databinding.BindingAdapter
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ItemTypeBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel
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
                val isEmptyBackGround =
                    attributes.getBoolean(R.styleable.TypeChip_emptyBackGround, true)
                initViews(iconSize, spacing, nameSize, isEmptyBackGround)
            }
        }

        private fun initViews(
            @Dimension iconSize: Float,
            @Dimension spacing: Float,
            @Dimension nameSize: Float,
            isEmptyBackGround: Boolean,
        ) = with(binding) {
            orientation = HORIZONTAL
            ivTypeNameIcon.layoutParams.apply {
                width = iconSize.toInt()
                height = iconSize.toInt()
            }
            spaceType.layoutParams.width = spacing.toInt()
            tvTypeName.textSize = nameSize
            isEmptyBackground = isEmptyBackGround
        }

        companion object {
            @JvmStatic
            @BindingAdapter("type")
            fun setTypeName(
                view: TypeChip,
                typeUiModel: TypeUiModel,
            ) {
                view.binding.type = typeUiModel
            }
        }
    }
