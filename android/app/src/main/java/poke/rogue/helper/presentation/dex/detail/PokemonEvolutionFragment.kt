package poke.rogue.helper.presentation.dex.detail

import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonEvolutionBinding
import poke.rogue.helper.presentation.base.BindingFragment

class PokemonEvolutionFragment : BindingFragment<FragmentPokemonEvolutionBinding>(R.layout.fragment_pokemon_evolution) {
    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}
