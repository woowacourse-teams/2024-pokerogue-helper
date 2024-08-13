package poke.rogue.helper.presentation.dex

import android.os.Bundle
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
}
