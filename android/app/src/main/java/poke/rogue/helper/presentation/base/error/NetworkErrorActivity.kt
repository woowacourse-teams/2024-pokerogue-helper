package poke.rogue.helper.presentation.base.error

import android.os.Bundle
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityNetworkErrorBinding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.util.context.isNetworkConnected
import poke.rogue.helper.presentation.util.context.toast
import poke.rogue.helper.presentation.util.event.RefreshEventBus
import poke.rogue.helper.presentation.util.view.setOnSingleClickListener

class NetworkErrorActivity :
    BindingActivity<ActivityNetworkErrorBinding>(R.layout.activity_network_error) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNetworkConnect()
    }

    private fun checkNetworkConnect() {
        binding.btnNetworkErrorRetry.setOnSingleClickListener {
            if (isNetworkConnected()) {
                RefreshEventBus.refresh()
                finish()
                return@setOnSingleClickListener
            }
            toast(getString(R.string.network_error_toast))
        }
    }
}
