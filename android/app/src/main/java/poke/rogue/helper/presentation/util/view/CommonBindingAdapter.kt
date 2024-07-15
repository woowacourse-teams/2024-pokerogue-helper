package poke.rogue.helper.presentation.util.view

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import poke.rogue.helper.R

@BindingAdapter("imageUrl")
fun ImageView.setImage(imageUrl: String?) {
    Glide.with(context)
        .load(imageUrl)
        .placeholder(R.drawable.icon_poke)
        .error(R.drawable.icon_error)
        .into(this)
}

@BindingAdapter("imageRes")
fun ImageView.setImageRes(imageRes: Int) {
    setImageResource(imageRes)
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
