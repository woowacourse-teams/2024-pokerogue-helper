package poke.rogue.helper.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModel을 생성하는 Factory
 *
 * sample
 * ```kotlin
 * class MyViewModel : ViewModel() {
 *   ...
 *   companion object {
 *      fun factory() = BaseViewModelFactory { MyViewModel() }
 *   }
 * }
 * ```
 */
class BaseViewModelFactory<VM : ViewModel>(
    private val creator: () -> VM,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = creator() as T
}