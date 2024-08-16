package poke.rogue.helper.presentation.util.view

import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.CircularProgressIndicator
import poke.rogue.helper.R
import poke.rogue.helper.presentation.util.context.colorOf

@BindingAdapter("imageUrl")
fun ImageView.setImage(imageUrl: String?) {
    Glide.with(context)
        .load(imageUrl)
        .placeholder(R.drawable.ic_pikachu_silhouette)
        .error(R.drawable.ic_ditto_silhouette)
        .into(this)
}

@BindingAdapter("svgUrl")
fun ImageView.loadSvgFromUrl(url: String?) {
    val imageLoader = ImageLoader.Builder(context)
        .components { add(SvgDecoder.Factory()) }
        .build()

    val request = ImageRequest.Builder(context)
        .data(url)
        .placeholder(R.drawable.ic_pikachu_silhouette)
        .error(R.drawable.ic_ditto_silhouette)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}

@BindingAdapter("cropImageUrl")
fun ImageView.setCroppedImage(imageUrl: String?) {
    Glide.with(context)
        .load(imageUrl)
        .placeholder(R.drawable.ic_pikachu_silhouette)
        .error(R.drawable.ic_ditto_silhouette)
        .transform(CropMarginTransformation())
        .into(this)
}

@BindingAdapter("imageUrlWithProgress", "progressIndicator")
fun ImageView.loadImageWithProgress(
    imageUrl: String?,
    progressIndicator: CircularProgressIndicator,
) {
    progressIndicator.visibility = View.VISIBLE

    Glide.with(context)
        .load(imageUrl)
        .listener(createProgressListener(progressIndicator))
        .error(R.drawable.ic_ditto_silhouette)
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
