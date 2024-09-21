package poke.rogue.helper.data.cache

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import poke.rogue.helper.data.exception.UnknownException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GlideImageCacher(private val context: Context) : ImageCacher {
    override suspend fun cacheImages(urls: List<String>) =
        coroutineScope {
            urls.forEach { url ->
                launch {
                    cacheImage(url)
                }
            }
        }

    /**
     * 백그라운드 작업에서는 submit() 메서드를 사용, 이 경우 RequestListener도 백그라운드 스레드에서 호출
     * UI 작업을 할 때는 into() 메서드를 사용, 이 경우 Main 스레드에서 RequestListener 이 호출
     */
    private suspend fun cacheImage(url: String) =
        suspendCancellableCoroutine<Unit> { con ->
            val requestListener =
                object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        con.resumeWithException(
                            e ?: UnknownException(IllegalStateException("이미지 로드 실패 url: $url")),
                        )
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        con.resume(Unit)
                        return false
                    }
                }

            val target =
                Glide.with(context)
                    .load(url)
                    .listener(requestListener)
                    .submit()

            con.invokeOnCancellation {
                Glide.with(context).clear(target)
            }
        }

    override suspend fun clear() {
        Glide.get(context).clearMemory()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: GlideImageCacher? = null

        fun init(context: Context) {
            require(instance == null) {
                "GlideImageCacher is already initialized"
            }
            instance = GlideImageCacher(context)
        }

        fun instance(): GlideImageCacher {
            return requireNotNull(instance) {
                "GlideImageCacher is not initialized"
            }
        }
    }
}
