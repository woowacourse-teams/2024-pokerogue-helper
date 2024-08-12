package poke.rogue.helper.presentation.base.error

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import poke.rogue.helper.R
import poke.rogue.helper.presentation.base.toolbar.ToolbarActivity
import poke.rogue.helper.presentation.util.context.startActivity
import poke.rogue.helper.presentation.util.context.toast
import poke.rogue.helper.presentation.util.repeatOnStarted

abstract class ErrorHandleActivity<T : ViewDataBinding>(
    @LayoutRes layoutRes: Int,
) :
    ToolbarActivity<T>(layoutRes) {
    abstract val errorViewModel: ErrorHandleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeErrorEvent()
    }

    protected open fun handleErrorEvent(event: ErrorEvent) {
        when (event) {
            is ErrorEvent.NetworkException -> startActivity<NetworkErrorActivity>()
            is ErrorEvent.UnknownError, is ErrorEvent.HttpException -> {
                toast(event.msg ?: getString(R.string.error_IO_Exception))
            }
        }
    }

    private fun observeErrorEvent() {
        repeatOnStarted {
            errorViewModel.commonErrorEvent.collect {
                handleErrorEvent(it)
            }
        }
    }
}
