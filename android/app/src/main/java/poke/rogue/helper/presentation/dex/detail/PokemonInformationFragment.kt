package poke.rogue.helper.presentation.dex.detail

import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonInformationBinding
import poke.rogue.helper.presentation.base.BindingFragment

class PokemonInformationFragment :
    BindingFragment<FragmentPokemonInformationBinding>(R.layout.fragment_pokemon_information) {
    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}
