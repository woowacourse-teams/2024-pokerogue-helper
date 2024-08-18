package poke.rogue.helper.ui.component

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.Dimension.Companion.DP
import androidx.annotation.Dimension.Companion.PX
import androidx.annotation.DrawableRes
import androidx.core.content.res.use
import androidx.core.graphics.ColorUtils
import androidx.databinding.BindingAdapter
import poke.rogue.helper.R
import poke.rogue.helper.presentation.util.context.colorOf
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.setOnSingleClickListener
import poke.rogue.helper.ui.layout.PaddingValues
import poke.rogue.helper.ui.layout.applyTo

class PokeChip
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : LinearLayout(context, attrs, defStyleAttr) {
        var chipId: Int = NO_ID
            private set

        private var leadingIcon: ImageView = ImageView(context)
        private var leadingSpacer: Space = Space(context)
        private val label: TextView = TextView(context)
        private var trailingIcon: ImageView = ImageView(context)
        private var trailingSpacer: Space = Space(context)

        init {
            context.obtainStyledAttributes(attrs, R.styleable.PokeChip).use { attributes ->
                attributes.apply {
                    val leadingIcon = getResourceId(R.styleable.PokeChip_leadingIcon, NO_ICON)
                    val leadingIconSize =
                        getDimensionPixelSize(R.styleable.PokeChip_leadingIconSize, 24.dp)
                    val leadingSpacing =
                        getDimensionPixelSize(R.styleable.PokeChip_leadingSpacing, 8.dp)

                    val label: String = getString(R.styleable.PokeChip_label) ?: ""
                    val labelSize = getDimension(R.styleable.PokeChip_labelSize, 16f)

                    val trailingIcon = getResourceId(R.styleable.PokeChip_trailingIcon, NO_ICON)
                    val trailingIconSize =
                        getDimensionPixelSize(R.styleable.PokeChip_trailingIconSize, 24.dp)
                    val trailingSpacing =
                        getDimensionPixelSize(R.styleable.PokeChip_trailingSpacing, 4.dp)

                    val labelColor =
                        getColor(
                            R.styleable.PokeChip_labelColor,
                            context.colorOf(R.color.poke_chip_text_default),
                        )
                    val containerColor =
                        getColor(
                            R.styleable.PokeChip_containerColor,
                            context.colorOf(R.color.poke_chip_background_default),
                        )
                    val strokeColor =
                        getColor(
                            R.styleable.PokeChip_strokeColor,
                            context.colorOf(R.color.poke_chip_stroke_default),
                        )
                    val selectedLabelColor =
                        getColor(
                            R.styleable.PokeChip_selectedLabelColor,
                            context.colorOf(R.color.poke_chip_text_selected),
                        )
                    val selectedStrokeColor =
                        getColor(
                            R.styleable.PokeChip_selectedStrokeColor,
                            context.colorOf(R.color.poke_chip_stroke_selected),
                        )
                    val selectedContainerColor =
                        getColor(
                            R.styleable.PokeChip_selectedContainerColor,
                            context.colorOf(R.color.poke_chip_background_selected),
                        )

                    val cornerRadius = getDimensionPixelSize(R.styleable.PokeChip_cornerRadius, 10.dp)
                    val strokeWidth = getDimensionPixelSize(R.styleable.PokeChip_strokeWidth, 1.dp)
                    // init views
                    initLeadingIcon(leadingIcon, leadingIconSize, leadingSpacing, label.isNotBlank())
                    initLabel(label, labelSize)
                    initTrailingIcon(
                        trailingIcon,
                        trailingIconSize,
                        trailingSpacing,
                        label.isNotBlank(),
                    )
                    initDrawableBackground(
                        containerColor,
                        selectedContainerColor,
                        strokeColor,
                        selectedStrokeColor,
                        cornerRadius,
                        strokeWidth,
                    )
                    initLabelColor(labelColor, selectedLabelColor)
                    initLayout()
                }
            }
        }

        private fun initPokeChip(chipSpec: Spec) {
            removeAllViews()
            chipId = chipSpec.id
            isSelected = chipSpec.isSelected
            val (leadingIconSize, leadingSpacing, labelSize, trailingSpacing, trailingIconSize) =
                chipSpec.sizes
            val colorSpec = chipSpec.colors
            initLeadingIcon(
                chipSpec.leadingIconRes ?: NO_ICON,
                leadingIconSize,
                leadingSpacing,
                chipSpec.label.isNotBlank(),
            )
            initLabel(chipSpec.label, labelSize.toFloat())
            initTrailingIcon(
                chipSpec.trailingIconRes ?: NO_ICON,
                trailingIconSize,
                trailingSpacing,
                chipSpec.label.isNotBlank(),
            )
            initDrawableBackground(
                containerColor = context.colorOf(colorSpec.containerColor),
                selectedContainerColor = context.colorOf(colorSpec.selectedContainerColor),
                strokeColor = context.colorOf(colorSpec.strokeColor),
                selectedStrokeColor = context.colorOf(colorSpec.selectedStrokeColor),
                cornerRadius = chipSpec.cornerRadius,
                strokeWidth = chipSpec.strokeWidth,
            )
            initLabelColor(
                labelColor = context.colorOf(colorSpec.labelColor),
                selectedLabelColor = context.colorOf(colorSpec.selectedLabelColor),
            )
            initLayout(chipSpec.padding)
            setOnSingleClickListener(duration = 200) {
                chipSpec.onSelect?.invoke(chipId)
            }
        }

        private fun initLayout(padding: PaddingValues = PaddingValues(8.dp)) {
            gravity = Gravity.CENTER_VERTICAL
            if (paddingStart == 0 && paddingTop == 0 && paddingEnd == 0 && paddingBottom == 0) {
                padding.applyTo(this)
            }
        }

        private fun initLeadingIcon(
            @DrawableRes leadingIconRes: Int,
            @Dimension(DP) leadingIconSize: Int,
            @Dimension(DP) leadingSpacing: Int,
            hasLabel: Boolean,
        ) {
            leadingIcon.setImageResource(leadingIconRes)
            leadingIcon.layoutParams = LayoutParams(leadingIconSize, leadingIconSize)
            leadingSpacer.layoutParams = LayoutParams(leadingSpacing, LayoutParams.WRAP_CONTENT)
            if (leadingIconRes != NO_ICON) {
                addView(leadingIcon)
                if (hasLabel) addView(leadingSpacer)
            }
        }

        private fun initLabel(
            label: String,
            @Dimension(PX) labelSize: Float,
        ) {
            this.label.text = label
            this.label.layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            this.label.textSize = labelSize
            this.label.paint.isFakeBoldText = isSelected
            if (label.isNotBlank()) {
                addView(this.label)
            }
        }

        private fun initTrailingIcon(
            @DrawableRes trailingIconRes: Int,
            @Dimension(DP) trailingIconSize: Int,
            @Dimension(DP) trailingSpacing: Int,
            hasLabel: Boolean,
        ) {
            trailingIcon.setImageResource(trailingIconRes)
            trailingIcon.layoutParams = LayoutParams(trailingIconSize, trailingIconSize)
            trailingSpacer.layoutParams = LayoutParams(trailingSpacing, LayoutParams.WRAP_CONTENT)
            if (trailingIconRes != NO_ICON) {
                if (hasLabel) addView(trailingSpacer)
                addView(trailingIcon)
            }
        }

        private fun initDrawableBackground(
            @ColorInt containerColor: Int,
            @ColorInt selectedContainerColor: Int,
            @ColorInt strokeColor: Int,
            @ColorInt selectedStrokeColor: Int,
            @Dimension(DP) cornerRadius: Int = 10.dp,
            @Dimension(DP) strokeWidth: Int = 1.dp,
        ) {
            val unSelectedDrawable =
                GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    this.cornerRadius = cornerRadius.toFloat()
                    setColor(containerColor)
                    setStroke(strokeWidth, strokeColor)
                }

            val selectedDrawable =
                GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    this.cornerRadius = cornerRadius.toFloat()
                    setColor(selectedContainerColor)
                    setStroke(strokeWidth, selectedStrokeColor)
                }

            val states =
                arrayOf(
                    intArrayOf(android.R.attr.state_selected),
                    intArrayOf(),
                )
            val stateListDrawable =
                StateListDrawable().apply {
                    addState(states[0], selectedDrawable)
                    addState(states[1], unSelectedDrawable)
                }
            val rippleColor = ColorUtils.setAlphaComponent(selectedContainerColor, 128)
            this.background =
                RippleDrawable(
                    ColorStateList.valueOf(rippleColor),
                    stateListDrawable,
                    null,
                )
        }

        private fun initLabelColor(
            @ColorInt labelColor: Int,
            @ColorInt selectedLabelColor: Int,
        ) {
            val textColorList =
                ColorStateList(
                    arrayOf(
                        intArrayOf(),
                        intArrayOf(android.R.attr.state_selected),
                    ),
                    intArrayOf(
                        labelColor,
                        selectedLabelColor,
                    ),
                )
            label.setTextColor(textColorList)
        }

        data class Spec(
            val id: Int = NO_ID,
            val label: String,
            @DrawableRes val leadingIconRes: Int? = null,
            @DrawableRes val trailingIconRes: Int? = null,
            val colors: PokeChipColors = PokeChipColors(),
            val sizes: PokeChipSizes = PokeChipSizes(),
            val padding: PaddingValues = PaddingValues(8.dp),
            @Dimension(DP) val strokeWidth: Int = 1.dp,
            @Dimension(DP) val cornerRadius: Int = 10.dp,
            val isSelected: Boolean = false,
            val onSelect: ((chipId: Int) -> Unit)? = null,
        ) {
            init {
                require(leadingIconRes != null || label.isNotBlank()) {
                    "leadingIconRes 와 label 중 하나는 반드시 있어야 합니다."
                }
            }
        }

        data class PokeChipColors(
            @ColorRes val labelColor: Int = R.color.poke_chip_text_default,
            @ColorRes val strokeColor: Int = R.color.poke_chip_stroke_default,
            @ColorRes val containerColor: Int = R.color.poke_chip_background_default,
            @ColorRes val selectedLabelColor: Int = R.color.poke_chip_text_selected,
            @ColorRes val selectedStrokeColor: Int = R.color.poke_chip_stroke_selected,
            @ColorRes val selectedContainerColor: Int = R.color.poke_chip_background_selected,
        )

        data class PokeChipSizes(
            @Dimension(DP) val leadingIconSize: Int = 24.dp,
            @Dimension(DP) val leadingSpacing: Int = 8.dp,
            @Dimension(PX) val labelSize: Int = 16,
            @Dimension(DP) val trailingSpacing: Int = 4.dp,
            @Dimension(DP) val trailingIconSize: Int = 24.dp,
        ) {
            init {
                require(leadingIconSize >= 0) { "leadingIconSize can't be negative" }
                require(leadingSpacing >= 0) { "leadingSpacing can't be negative" }
                require(trailingIconSize >= 0) { "trailingIconSize can't be negative" }
                require(trailingSpacing >= 0) { "trailingSpacing can't be negative" }
                require(labelSize >= 0) { "labelSize can't be negative" }
            }
        }

        companion object {
            private const val NO_ICON = 0
            private const val NO_ID = -1

            @JvmStatic
            @BindingAdapter("pokeChipSpec")
            fun PokeChip.bindPokeChip(chipSpec: Spec) {
                initPokeChip(chipSpec)
            }
        }
    }
