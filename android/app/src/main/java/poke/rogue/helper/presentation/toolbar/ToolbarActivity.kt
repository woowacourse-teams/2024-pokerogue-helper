package poke.rogue.helper.presentation.toolbar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import poke.rogue.helper.R
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.util.context.drawableOf
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.logClickEvent

abstract class ToolbarActivity<T : ViewDataBinding>(
    @LayoutRes layoutRes: Int,
) : BindingActivity<T>(layoutRes) {
    protected abstract val toolbar: Toolbar?
    private val logger: AnalyticsLogger = analyticsLogger()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar?.let {
            setSupportActionBar(it)
            it.overflowIcon = drawableOf(R.drawable.ic_menu)
            supportActionBar?.setDisplayShowTitleEnabled(true)
        }
    }

    override fun onMenuOpened(
        featureId: Int,
        menu: Menu,
    ): Boolean {
        logger.logClickEvent(CLICK_MENU)
        return super.onMenuOpened(featureId, menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_toolbar_pokerogue -> {
                logger.logClickEvent(NAVIGATE_TO_POKE_ROGUE)
                navigateToLink(R.string.home_pokerogue_url)
            }

            R.id.item_toolbar_feedback -> {
                logger.logClickEvent(NAVIGATE_TO_FEED_BACK)
                navigateToLink(R.string.home_pokeroque_surey_url)
            }

            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
            }
        }
        return true
    }

    private fun navigateToLink(urlRes: Int) {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse(stringOf(urlRes)),
        ).also { startActivity(it) }
    }

    companion object {
        private const val CLICK_MENU = "Click_Menu_Button"
        private const val NAVIGATE_TO_POKE_ROGUE = "Nav_Toolbar_To_PokeRogue_Game"
        private const val NAVIGATE_TO_FEED_BACK = "Nav_FeedBack"
    }
}
