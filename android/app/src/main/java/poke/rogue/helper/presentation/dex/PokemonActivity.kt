package poke.rogue.helper.presentation.dex

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityPokemonBinding
import poke.rogue.helper.presentation.base.BindingActivity

class PokemonActivity : BindingActivity<ActivityPokemonBinding>(R.layout.activity_pokemon) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<PokemonListFragment>(R.id.fragment_container_pokemon)
            }
        }
    }

    override fun toolBar(): Toolbar = binding.toolbarDex

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, PokemonActivity::class.java)
        }
    }
}
