package poke.rogue.helper.presentation.util.view

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.progressindicator.CircularProgressIndicator
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

@BindingAdapter("imageUrl", "progressIndicator")
fun ImageView.loadImageWithProgress(
    url: String?,
    progressIndicator: CircularProgressIndicator,
) {
    progressIndicator.visibility = View.VISIBLE

    Glide.with(context)
        .load(url)
        .listener(
            object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    progressIndicator.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    progressIndicator.visibility = View.GONE
                    return false
                }
            },
        )
//        .error(R.drawable.icon_error)
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
