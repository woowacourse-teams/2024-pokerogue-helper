package poke.rogue.helper.presentation.base.error

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import poke.rogue.helper.R
import poke.rogue.helper.presentation.base.toolbar.ToolbarFragment
import poke.rogue.helper.presentation.util.fragment.startActivity
import poke.rogue.helper.presentation.util.fragment.toast
import poke.rogue.helper.presentation.util.repeatOnStarted

abstract class ErrorHandleFragment<T : ViewDataBinding>(
    @LayoutRes layoutRes: Int,
) : ToolbarFragment<T>(layoutRes) {
    abstract val errorViewModel: ErrorHandleViewModel

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        observeErrorEvent()
    }

    protected open fun handleErrorEvent(event: ErrorEvent) {
        when (event) {
            is ErrorEvent.NetworkException ->
                startActivity<NetworkErrorActivity> {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                }
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
