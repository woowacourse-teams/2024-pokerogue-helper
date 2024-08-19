package poke.rogue.helper.presentation.biome.detail.gym

import android.os.Bundle
import android.view.View
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentBiomeGymPokemonBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.biome.model.BiomeDetailUiModel

class BiomeGymFragment :
    BindingFragment<FragmentBiomeGymPokemonBinding>(R.layout.fragment_biome_gym_pokemon) {
    private val gymPokemonAdapter: BiomeGymAdapter by lazy { BiomeGymAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        BiomeDetailUiModel.DUMMY.gymPokemons.let(gymPokemonAdapter::submitList)
        binding.rvBiomeGym.adapter = gymPokemonAdapter
    }
}
