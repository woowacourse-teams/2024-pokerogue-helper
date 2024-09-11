package poke.rogue.helper.presentation.util.view

import android.graphics.Bitmap
import android.graphics.Color
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class CropMarginTransformation() :
    BitmapTransformation() {
    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int,
    ): Bitmap {
        return noMarginBitmap(toTransform)
    }

    private fun noMarginBitmap(original: Bitmap): Bitmap {
        val width = original.width
        val height = original.height
        val pixels = IntArray(width * height)
        original.getPixels(pixels, 0, width, 0, 0, width, height)
        var minX = width
        var minY = height
        var maxX = 0
        var maxY = 0

        for (y in 0 until height) {
            for (x in 0 until width) {
                val currentColor = pixels[y * width + x]
                if (currentColor.hasColor()) {
                    minX = minX.coerceAtMost(x)
                    minY = minY.coerceAtMost(y)
                    maxX = maxX.coerceAtLeast(x)
                    maxY = maxY.coerceAtLeast(y)
                }
            }
        }

        return Bitmap.createBitmap(original, minX, minY, maxX - minX + 1, maxY - minY + 1)
    }

    private fun Int.hasColor() = (this != Color.TRANSPARENT)

    override fun equals(other: Any?): Boolean {
        return other is CropMarginTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    companion object {
        private val ID: String =
            CropMarginTransformation::class.java.canonicalName
                ?: CropMarginTransformation::class.java.simpleName
        private val ID_BYTES = ID.toByteArray(Charsets.UTF_8)
    }
}
