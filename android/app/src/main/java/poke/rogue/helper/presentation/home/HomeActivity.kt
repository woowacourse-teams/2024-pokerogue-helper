package poke.rogue.helper.presentation.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.install.model.ActivityResult.RESULT_IN_APP_UPDATE_FAILED
import org.koin.androidx.viewmodel.ext.android.viewModel
import poke.rogue.helper.R
import poke.rogue.helper.analytics.AnalyticsEvent
import poke.rogue.helper.databinding.ActivityHomeBinding
import poke.rogue.helper.presentation.ability.AbilityActivity
import poke.rogue.helper.presentation.base.toolbar.ToolbarActivity
import poke.rogue.helper.presentation.battle.BattleActivity
import poke.rogue.helper.presentation.biome.BiomeActivity
import poke.rogue.helper.presentation.dex.PokemonListActivity
import poke.rogue.helper.presentation.tip.TipActivity
import poke.rogue.helper.presentation.type.TypeActivity
import poke.rogue.helper.presentation.util.context.startActivity
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.context.toast
import poke.rogue.helper.presentation.util.logClickEvent
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.update.UpdateManager

class HomeActivity : ToolbarActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var updateManager: UpdateManager

    override val toolbar: Toolbar
        get() = binding.toolbarHome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initUpdateManager()
        initObservers()
    }

    private fun initViews() =
        with(binding) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            actionHandler = viewModel
        }

    private fun initUpdateManager() {
        updateManager = UpdateManager(applicationContext) { showUpdateComplete() }
        lifecycle.addObserver(updateManager)

        val appUpdateLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartIntentSenderForResult(),
            ) { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        logger.logClickEvent(UPDATE_AGREE)
                        toast(R.string.update_result_ok)
                    }

                    RESULT_CANCELED -> {
                        logger.logClickEvent(UPDATE_DISAGREE)
                    }

                    RESULT_IN_APP_UPDATE_FAILED -> {
                        logger.logEvent(
                            AnalyticsEvent(type = UPDATE_ERROR),
                        )
                        toast(R.string.update_result_failed)
                    }
                }
            }
        updateManager.checkForAppUpdates(appUpdateLauncher)
    }

    private fun showUpdateComplete() {
        Snackbar.make(binding.root, R.string.update_snackBar_title, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.update_snackBar_action_text) {
                updateManager.completeUpdate()
            }
            .show()
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.navigationEvent.collect { state ->
                when (state) {
                    is HomeNavigateEvent.ToType ->
                        startActivity<TypeActivity> {
                            logger.logClickEvent(NAVIGATE_TO_TYPE)
                        }

                    is HomeNavigateEvent.ToDex ->
                        startActivity<PokemonListActivity> {
                            logger.logClickEvent(NAVIGATE_TO_DEX)
                        }

                    is HomeNavigateEvent.ToAbility ->
                        startActivity<AbilityActivity> {
                            logger.logClickEvent(NAVIGATE_TO_ABILITY)
                        }

                    is HomeNavigateEvent.ToTip ->
                        startActivity<TipActivity> {
                            logger.logClickEvent(NAVIGATE_TO_TIP)
                        }

                    is HomeNavigateEvent.ToBiome ->
                        startActivity<BiomeActivity> {
                            logger.logClickEvent(NAVIGATE_TO_BIOME)
                        }

                    is HomeNavigateEvent.ToItem -> {
                        toast("Coming soon")
                    }

                    is HomeNavigateEvent.ToBattle -> {
                        startActivity<BattleActivity> {
                            logger.logClickEvent(NAVIGATE_TO_BATTLE)
                        }
                    }

                    is HomeNavigateEvent.ToLogo -> navigateToPokeRogue()
                }
            }
        }
    }

    private fun navigateToPokeRogue() {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse(stringOf(R.string.home_pokerogue_url)),
        ).also {
            startActivity(it)
            logger.logClickEvent(NAVIGATE_TO_POKE_ROGUE)
        }
    }

    companion object {
        private const val NAVIGATE_TO_POKE_ROGUE = "Nav_Logo_To_PokeRogue_Game"
        private const val NAVIGATE_TO_TYPE = "Nav_Type"
        private const val NAVIGATE_TO_DEX = "Nav_Dex"
        private const val NAVIGATE_TO_ABILITY = "Nav_Ability"
        private const val NAVIGATE_TO_TIP = "Nav_Tip"
        private const val NAVIGATE_TO_BIOME = "Nav_Biome"
        private const val NAVIGATE_TO_ITEM = "Nav_Item"
        private const val NAVIGATE_TO_BATTLE = "Nav_Battle"
        private const val UPDATE_AGREE = "Update_Agree"
        private const val UPDATE_DISAGREE = "Update_Disagree"
        private const val UPDATE_ERROR = "Update_Error"

        fun intent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}
