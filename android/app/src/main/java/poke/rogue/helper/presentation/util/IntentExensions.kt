package poke.rogue.helper.presentation.util

import android.content.Intent
import android.os.Parcelable
import androidx.core.content.IntentCompat
import java.io.Serializable

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? {
    return IntentCompat.getParcelableExtra(this, key, T::class.java)
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? {
    return IntentCompat.getSerializableExtra(this, key, T::class.java)
}
