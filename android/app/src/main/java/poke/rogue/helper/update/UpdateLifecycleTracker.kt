package poke.rogue.helper.update

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class UpdateLifecycleTracker(private val updateManager: UpdateManager) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        updateManager.registerInstallStateUpdateListener()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        updateManager.unregisterInstallStateUpdateListener()
    }
}
