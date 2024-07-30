package poke.rogue.helper.presentation.util.view

import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import poke.rogue.helper.R
import poke.rogue.helper.presentation.util.context.colorOf

@BindingAdapter("imageUrl")
fun ImageView.setImage(imageUrl: String?) {
    Glide.with(context)
        .load(imageUrl)
        .placeholder(R.drawable.icon_poke)
        .error(R.drawable.icon_error)
        .into(this)
}

@BindingAdapter("cropImageUrl")
fun ImageView.setCroppedImage(imageUrl: String?) {
    Glide.with(context)
        .load(imageUrl)
        .placeholder(R.drawable.icon_poke)
        .error(R.drawable.icon_error)
        .transform(CropMarginTransformation())
        .into(this)
}

@BindingAdapter("imageRes")
fun ImageView.setImage(imageRes: Int) {
    setImageResource(imageRes)
}

@BindingAdapter("backgroundColorRes")
fun View.setBackGroundColorRes(
    @ColorRes backgroundColorRes: Int,
) {
    if (backgroundColorRes == 0) return
    setBackgroundColor(context.colorOf(backgroundColorRes))
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("backgroundTintRes")
fun View.setBackgroundTint(
    @ColorRes colorRes: Int?,
) {
    colorRes?.let {
        backgroundTintList = ContextCompat.getColorStateList(context, it)
    }
}
