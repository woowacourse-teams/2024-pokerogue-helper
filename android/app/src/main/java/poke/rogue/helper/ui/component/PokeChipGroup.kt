package poke.rogue.helper.ui.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.Space
import androidx.annotation.Dimension
import androidx.annotation.Dimension.Companion.DP
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.JustifyContent
import poke.rogue.helper.R
import poke.rogue.helper.presentation.util.context.colorOf
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.ui.component.PokeChip.Companion.bindPokeChip

class PokeChipGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FlexboxLayout(context, attrs, defStyleAttr) {

    private val chipViews = mutableListOf<PokeChip>()
    var direction: PokeChipGroupDirection = PokeChipGroupDirection.ROW
    var itemSpacing: Int = 0
    var lineSpacing: Int = 0

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PokeChipGroup,
            0, 0
        ).apply {
            try {
                direction =
                    PokeChipGroupDirection.from(getInt(R.styleable.PokeChipGroup_direction, 0))
                itemSpacing = getDimensionPixelSize(R.styleable.PokeChipGroup_itemSpacing, 0.dp)
                lineSpacing = getDimensionPixelSize(R.styleable.PokeChipGroup_lineSpacing, 0.dp)
            } finally {
                recycle()
            }
        }

        flexWrap = FlexWrap.WRAP
        alignItems = AlignItems.CENTER
        flexDirection = direction.toFlexDirection()
        justifyContent = JustifyContent.FLEX_START
        val horizontalDivider = spacingDrawable(lineSpacing)
        setDividerDrawable(horizontalDivider)
        setShowDivider(SHOW_DIVIDER_MIDDLE)
    }

    fun submitList(
        specs: List<PokeChip.PokeChipSpec>,
        onSelect: ((chipId: Int) -> Unit)? = null
    ) {
        if (chipViews.isEmpty()) {
            addChips(specs, onSelect)
        } else {
            updateChips(specs)
        }
    }

    private fun addChips(
        specs: List<PokeChip.PokeChipSpec>,
        onSelect: ((chipId: Int) -> Unit)?
    ) {
        removeAllViews()
        chipViews.clear()
        specs.forEach { spec ->
            addChip(spec, chipViews.toList(), onSelect)
        }
    }

    private fun addChip(
        spec: PokeChip.PokeChipSpec,
        originalChipViews: List<PokeChip>,
        onSelect: ((chipId: Int) -> Unit)?
    ) {
        require(originalChipViews.any { it.chipId == spec.id }.not()) {
            "id=${spec.id}인 chip이 이미 존재합니다."
        }
        val chip = PokeChip(context)
        onSelect?.let {
            chip.setOnClickListener { onSelect(spec.id) }
        }
        chip.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        chip.bindPokeChip(spec)
        addView(chip)
        val spacer = Space(context)
        spacer.layoutParams = LinearLayout.LayoutParams(itemSpacing, itemSpacing)
        addView(spacer)
        chipViews.add(chip)
    }

    private fun updateChips(specs: List<PokeChip.PokeChipSpec>) {
        require(chipViews.all { chip -> specs.any { it.id == chip.chipId } }) {
            "업데이트할 chip이 존재하지 않습니다."
        }

        specs.forEach { spec ->
            chipViews.find { it.chipId == spec.id }?.bindPokeChip(spec)
        }
    }

    private fun spacingDrawable(@Dimension(unit = DP) height: Int): Drawable {
        val drawable = ShapeDrawable(RectShape())
        drawable.intrinsicHeight = height
        drawable.paint.color = context.colorOf(android.R.color.transparent)
        return drawable
    }

    data class PokeChipGroupSpec(
        val direction: PokeChipGroupDirection = PokeChipGroupDirection.ROW,
        @Dimension(DP) val itemSpacing: Int,
        @Dimension(DP) val lineSpacing: Int,
    ) {
        init {
            require(itemSpacing >= 0) {
                "chip 사이 간격은 0 이상이어야 합니다."
            }
            require(lineSpacing >= 0) {
                "line 사이 간격은 0 이상이어야 합니다."
            }
        }
    }

    enum class PokeChipGroupDirection {
        ROW,
        COLUMN;

        fun toFlexDirection(): Int {
            return when (this) {
                ROW -> FlexDirection.ROW
                COLUMN -> FlexDirection.COLUMN
            }
        }

        companion object {
            fun from(value: Int): PokeChipGroupDirection {
                return when (value) {
                    0 -> ROW
                    1 -> COLUMN
                    else -> error("PokeChipGroupDirection - Unknown value: $value")
                }
            }
        }
    }
}