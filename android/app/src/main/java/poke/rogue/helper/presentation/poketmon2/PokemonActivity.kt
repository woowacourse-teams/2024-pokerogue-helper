package poke.rogue.helper.presentation.poketmon2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityPokemonBinding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.util.context.drawableOf
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.context.toast

class PokemonActivity : BindingActivity<ActivityPokemonBinding>(R.layout.activity_pokemon) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<PokemonListFragment>(R.id.fragment_container_pokemon)
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
                onBackPressedDispatcher.onBackPressed()
            }
        }
        return true
    }

    private fun initViews() =
        with(binding) {
            setSupportActionBar(toolbarHome.toolbar)
            toolbarHome.toolbar.overflowIcon = drawableOf(R.drawable.ic_menu)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

    private fun navigateToPokeRogue() {
        toast(R.string.toolbar_pokerogue)
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(stringOf(R.string.home_pokerogue_url)))
        startActivity(intent)
    }
}
