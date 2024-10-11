package poke.rogue.helper.update

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
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
    private val context: Context
) {
    private val appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(context)
    private val updateType = AppUpdateType.FLEXIBLE

    private val installStateUpdateListener = InstallStateUpdatedListener { state ->
        when (state.installStatus()) {
            InstallStatus.INSTALLING -> Timber.i("Update is downloading")

            InstallStatus.DOWNLOADED -> {
                Timber.i("Update installed successfully")
                appUpdateManager.completeUpdate()
            }

            InstallStatus.CANCELED -> Timber.e("Update was cancelled")
        }
    }

    fun checkForAppUpdates(
        appUpdateLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if (checkForAppUpdate(info)) {
                val updateOptions = AppUpdateOptions.newBuilder(updateType).build()
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    appUpdateLauncher,
                    updateOptions
                )
            }
        }
    }

    private fun checkForAppUpdate(info: AppUpdateInfo): Boolean {
        val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
        val isUpdateAllowed = info.isUpdateTypeAllowed(updateType)
        return isUpdateAvailable && isUpdateAllowed
    }


    fun registerInstallStateUpdateListener() {
        appUpdateManager.registerListener(installStateUpdateListener)
    }

    fun unregisterInstallStateUpdateListener() {
        appUpdateManager.unregisterListener(installStateUpdateListener)
    }
}
