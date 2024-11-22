package poke.rogue.helper.presentation.type.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ItemTypeParallelogramBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.util.context.colorOf
import poke.rogue.helper.presentation.util.view.dp
import kotlin.math.tan

/**
 * ```xml
 *  <poke.rogue.helper.presentation.type.view.PokeParallelogramLayout
 *             android:layout_width="match_parent"
 *             android:layout_height="70dp"
 *             app:contentColor="@color/poke_bug"
 *             app:layout_constraintBottom_toBottomOf="parent"
 *             app:layout_constraintEnd_toEndOf="parent"
 *             app:layout_constraintStart_toStartOf="parent"
 *             app:layout_constraintTop_toTopOf="parent">
 *
 *             <ImageView
 *                 android:layout_width="40dp"
 *                 android:layout_height="40dp"
 *                 android:elevation="10dp"
 *                 android:src="@drawable/icon_type_bug"
 *                 app:layout_constraintBottom_toBottomOf="parent"
 *                 app:layout_constraintEnd_toEndOf="parent"
 *                 app:layout_constraintStart_toStartOf="parent"
 *                 app:layout_constraintTop_toTopOf="parent" />
 *
 *         </poke.rogue.helper.presentation.type.view.PokeParallelogramLayout>
 * ```
 * */
class PokeParallelogramLayout
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0,
    ) : ConstraintLayout(context, attrs, defStyle) {
        private val boarderPaint: Paint = Paint()
        private val contentPaint: Paint = Paint()
        private var angleDegree: Double = 0.0
        private val binding: ItemTypeParallelogramBinding by lazy {
            ItemTypeParallelogramBinding.inflate(LayoutInflater.from(context), this, true)
        }

        init {
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.Parallogram,
                0,
                0,
            ).apply {
                try {
                    val topAngle = getInteger(R.styleable.Parallogram_topAngle, 110)
                    require(topAngle >= 90) { "(topAngle=$topAngle) - 평행사변형의 윗 각도는 90도 이상이어야 합니다." }
                    initPaint(
                        contentColor =
                            getColor(
                                R.styleable.Parallogram_contentColor,
                                context.colorOf(android.R.color.transparent),
                            ),
                        borderColor =
                            getColor(
                                R.styleable.Parallogram_borderColor,
                                context.colorOf(android.R.color.transparent),
                            ),
                        borderWidth =
                            getDimension(
                                R.styleable.Parallogram_borderWidth,
                                0f.dp,
                            ),
                    )
                    angleDegree = topAngle.toDouble() - 90
                } finally {
                    recycle()
                }
            }
        }

        override fun dispatchDraw(canvas: Canvas) {
            val path =
                Path().apply {
                    val offset = tan(Math.toRadians(angleDegree)).toFloat() * height
                    moveTo(offset, 0f)
                    lineTo(width.toFloat(), 0f)
                    lineTo(width.toFloat() - offset, height.toFloat())
                    lineTo(0f, height.toFloat())
                    lineTo(offset, 0f)
                    close()
                }
            canvas.drawPath(path, boarderPaint)
            canvas.drawPath(path, contentPaint)
            canvas.save()
            canvas.clipPath(path)
            super.dispatchDraw(canvas)
            canvas.restore()
        }

        private fun initPaint(
            @ColorInt contentColor: Int,
            @ColorInt borderColor: Int,
            @Dimension(unit = Dimension.PX) borderWidth: Float,
        ) {
            contentPaint.apply {
                color = contentColor
                style = Paint.Style.FILL
                strokeJoin = Paint.Join.ROUND
            }
            if (borderWidth == 0f) return
            boarderPaint.apply {
                isAntiAlias = true
                color = borderColor
                style = Paint.Style.STROKE
                strokeWidth = borderWidth
            }
        }

        private fun bindType(type: TypeUiModel) {
            binding.type = type
            contentPaint.color = context.colorOf(type.typeColor)
            invalidate()
        }

        private fun bindListener(clickListener: OnClickListener) {
            binding.btnTopRight.setOnClickListener(clickListener)
        }

        companion object {
            @JvmStatic
            @BindingAdapter("typeSelection", "typeSelectionListener", requireAll = false)
            fun setSelectedType(
                view: PokeParallelogramLayout,
                type: TypeUiModel?,
                clickListener: OnClickListener?,
            ) {
                type?.let { view.bindType(it) }
                clickListener?.let { view.bindListener(clickListener) }
            }
        }
    }
