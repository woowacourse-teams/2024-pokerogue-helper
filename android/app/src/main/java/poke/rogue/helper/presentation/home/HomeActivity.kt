package poke.rogue.helper.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.databinding.ActivityHomeBinding
import poke.rogue.helper.presentation.ability.AbilityActivity
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.dex.PokemonActivity
import poke.rogue.helper.presentation.tip.TipActivity
import poke.rogue.helper.presentation.type.TypeActivity
import poke.rogue.helper.presentation.util.context.drawableOf
import poke.rogue.helper.presentation.util.context.startActivity
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.context.toast
import poke.rogue.helper.presentation.util.logClickEvent
import poke.rogue.helper.presentation.util.repeatOnStarted

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private val logger: AnalyticsLogger = analyticsLogger()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbarHome.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initViews()
        initObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_toolbar_pokerogue -> {
                navigateToPokeRogue()
            }

            R.id.item_toolbar_feedback -> {
                toast(R.string.toolbar_feedback)
            }
        }
        return true
    }

    private fun initViews() =
        with(binding) {
            setSupportActionBar(toolbarHome.toolbar)
            toolbarHome.toolbar.overflowIcon = drawableOf(R.drawable.ic_menu)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            actionHandler = viewModel
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
                        startActivity<PokemonActivity> {
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
        private const val NAVIGATE_TO_POKE_ROGUE = "To_PokeRogue_Game"
        private const val NAVIGATE_TO_TYPE = "To_Type"
        private const val NAVIGATE_TO_DEX = "To_Dex"
        private const val NAVIGATE_TO_ABILITY = "To_Ability"
        private const val NAVIGATE_TO_TIP = "To_Tip"
    }
}
