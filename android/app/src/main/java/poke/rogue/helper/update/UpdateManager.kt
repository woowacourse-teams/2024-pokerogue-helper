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
    private val onDownloadComplete: () -> Unit,
) : DefaultLifecycleObserver {
    private val appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(context)
    private val updateType = AppUpdateType.FLEXIBLE
    private val sharedPreferences = context.getSharedPreferences(UPDATE_TIME, Context.MODE_PRIVATE)

    private val installStateUpdateListener =
        InstallStateUpdatedListener { state ->
            when (state.installStatus()) {
                InstallStatus.INSTALLING -> Timber.i("Update is downloading")

                InstallStatus.DOWNLOADED -> {
                    onDownloadComplete()
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
        if (!isTimeToShowUpdateDialog()) {
            return
        }

        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if (shouldAppUpdate(info)) {
                val updateOptions = AppUpdateOptions.newBuilder(updateType).build()
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    appUpdateLauncher,
                    updateOptions,
                )
                sharedPreferences.edit().putLong(KEY_DIALOG, System.currentTimeMillis()).apply()
            }
        }
    }

    private fun shouldAppUpdate(info: AppUpdateInfo): Boolean {
        val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
        val isUpdateAllowed = info.isUpdateTypeAllowed(updateType)
        return isUpdateAvailable && isUpdateAllowed
    }

    private fun isTimeToShowUpdateDialog(): Boolean {
        val lastDialogTime = sharedPreferences.getLong(KEY_DIALOG, 0L)
        val currentTime = System.currentTimeMillis()
        return currentTime - lastDialogTime > UPDATE_DIALOG_INTERVAL
    }

    fun completeUpdate() {
        appUpdateManager.completeUpdate()
    }

    private fun registerInstallStateUpdateListener() {
        appUpdateManager.registerListener(installStateUpdateListener)
    }

    private fun unregisterInstallStateUpdateListener() {
        appUpdateManager.unregisterListener(installStateUpdateListener)
    }

    companion object {
        private const val UPDATE_TIME = "update_time"
        private const val KEY_DIALOG = "LAST_UPDATE_DIALOG"
        const val UPDATE_DIALOG_INTERVAL = 24 * 60 * 60 * 1000
    }
}
