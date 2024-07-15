package poke.rogue.helper.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityHomeBinding
import poke.rogue.helper.presentation.ability.AbilityActivity
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.poketmon2.PokemonActivity
import poke.rogue.helper.presentation.type.TypeActivity
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.context.toast

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbarHome.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        initClickListeners()
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

    private fun initClickListeners() {
        binding.apply {
            ibtnHomeLogo.setOnClickListener { navigateToPokeRogue() }
            cvHomeType.setOnClickListener {
                navigateToType()
            }
            cvHomeDex.setOnClickListener {
                navigateToDex()
            }
            cvHomeAbility.setOnClickListener {
                navigateToAbility()
            }
            cvHomeTip.setOnClickListener { toast("꿀팁 페이지로 이동") }
        }
    }

    private fun navigateToPokeRogue() {
        toast(R.string.toolbar_pokerogue)
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(stringOf(R.string.home_pokerogue_url)))
        startActivity(intent)
    }

    private fun navigateToType() {
        TypeActivity.intent(this).apply {
            startActivity(this)
        }
    }

    private fun navigateToDex() {
        PokemonActivity.intent(this).apply {
            startActivity(this)
        }
    }

    private fun navigateToAbility() {
        AbilityActivity.intent(this).apply {
            startActivity(this)
        }
    }
}
