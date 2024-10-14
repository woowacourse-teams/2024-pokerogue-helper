package poke.rogue.helper.update

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import timber.log.Timber

class UpdateManager(
    private val context: Context,
) : DefaultLifecycleObserver {
    private val appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(context)
    private val updateType = AppUpdateType.FLEXIBLE

    private val installStateUpdateListener =
        InstallStateUpdatedListener { state ->
            when (state.installStatus()) {
                InstallStatus.INSTALLING -> Timber.i("Update is downloading")

                InstallStatus.DOWNLOADED -> {
                    Timber.i("Update installed successfully")
                    appUpdateManager.completeUpdate()
                }

                InstallStatus.CANCELED -> Timber.e("Update was cancelled")
            }
        }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        registerInstallStateUpdateListener()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        unregisterInstallStateUpdateListener()
    }

    fun checkForAppUpdates(appUpdateLauncher: ActivityResultLauncher<IntentSenderRequest>) {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if (checkForAppUpdate(info)) {
                val updateOptions = AppUpdateOptions.newBuilder(updateType).build()
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    appUpdateLauncher,
                    updateOptions,
                )
            }
        }
    }

    private fun checkForAppUpdate(info: AppUpdateInfo): Boolean {
        val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
        val isUpdateAllowed = info.isUpdateTypeAllowed(updateType)
        return isUpdateAvailable && isUpdateAllowed
    }

    private fun registerInstallStateUpdateListener() {
        appUpdateManager.registerListener(installStateUpdateListener)
    }

    private fun unregisterInstallStateUpdateListener() {
        appUpdateManager.unregisterListener(installStateUpdateListener)
    }
}
