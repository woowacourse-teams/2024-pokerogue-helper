package poke.rogue.helper.presentation.splash

import android.annotation.SuppressLint
import android.os.Bundle
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityPokemonIntroBinding
import poke.rogue.helper.presentation.base.BindingActivity

@SuppressLint("CustomSplashScreen")
class PokemonIntroActivity :
    BindingActivity<ActivityPokemonIntroBinding>(R.layout.activity_pokemon_intro) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}