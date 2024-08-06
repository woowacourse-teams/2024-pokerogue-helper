package poke.rogue.helper.presentation.toolbar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import poke.rogue.helper.R
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.util.fragment.drawableOf
import poke.rogue.helper.presentation.util.logClickEvent

abstract class ToolbarFragment<T : ViewDataBinding>(
    @LayoutRes layoutRes: Int,
) : BindingFragment<T>(layoutRes) {
    protected abstract val toolbar: Toolbar?
    private val logger: AnalyticsLogger = analyticsLogger()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar?.apply {
            inflateMenu(R.menu.menu_toolbar)
            overflowIcon = drawableOf(R.drawable.ic_menu)
            setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.item_toolbar_pokerogue -> {
                        logger.logClickEvent(NAVIGATE_TO_POKE_ROGUE)
                        navigateToLink(R.string.home_pokerogue_url)
                    }

                    R.id.item_toolbar_feedback -> {
                        logger.logClickEvent(NAVIGATE_TO_FEED_BACK)
                        navigateToLink(R.string.home_pokeroque_surey_url)
                    }
                }
                true
            }
        }
    }

    private fun navigateToLink(urlRes: Int) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(urlRes)))
        startActivity(intent)
    }

    companion object {
        private const val NAVIGATE_TO_POKE_ROGUE = "Nav_Toolbar_To_PokeRogue_Game"
        private const val NAVIGATE_TO_FEED_BACK = "Nav_FeedBack"
    }
}
