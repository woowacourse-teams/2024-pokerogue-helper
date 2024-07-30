package poke.rogue.helper.presentation.ability

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityAbilityBinding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.util.context.drawableOf
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.context.toast

class AbilityActivity : BindingActivity<ActivityAbilityBinding>(R.layout.activity_ability) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<AbilityFragment>(R.id.fragment_container_ability)
            }
        }
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

            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    private fun initViews() =
        with(binding) {
            setSupportActionBar(toolbarAbility.toolbar)
            toolbarAbility.toolbar.overflowIcon = drawableOf(R.drawable.ic_menu)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

    private fun navigateToPokeRogue() {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(stringOf(R.string.home_pokerogue_url)))
        startActivity(intent)
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, AbilityActivity::class.java)
        }
    }
}
