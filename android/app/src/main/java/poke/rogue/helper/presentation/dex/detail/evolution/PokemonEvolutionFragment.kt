package poke.rogue.helper.presentation.dex.detail.evolution

import android.os.Bundle
import android.view.View
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonEvolutionBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.dex.model.EvolutionsUiModel.Companion.DUMMY_EVE_EVOLUTION

class PokemonEvolutionFragment : BindingFragment<FragmentPokemonEvolutionBinding>(R.layout.fragment_pokemon_evolution) {
    private val evolutionDepth0Adapter by lazy { EvolutionAdapter() }
    private val evolutionDepth1Adapter by lazy { EvolutionAdapter() }
    private val evolutionDepth2Adapter by lazy { EvolutionAdapter() }
    private val evolutionDepth3Adapter by lazy { EvolutionAdapter() }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        val evolutionsUiModel = DUMMY_EVE_EVOLUTION
        // 테스트 하고 싶으시면 아래 더미 데이터 중 하나를 선택해서 넣어보세요.
        // DUMMY_PICAKCHU_EVOLUTION
        // DUMMY_PSYDUCK_EVOLUTION
        // DUMMY_EVE_EVOLUTION

        binding.evolutions = evolutionsUiModel
        evolutionsUiModel.apply {
            evolutions(depth = 0).let(evolutionDepth0Adapter::submitList)
            evolutions(depth = 1).let(evolutionDepth1Adapter::submitList)
            evolutions(depth = 2).let(evolutionDepth2Adapter::submitList)
            evolutions(depth = 3).let(evolutionDepth3Adapter::submitList)
        }
    }

    private fun initAdapter() {
        binding.apply {
            rvPokemonEvolutionDepth0.recyclerView.adapter = evolutionDepth0Adapter
            rvPokemonEvolutionDepth1.recyclerView.adapter = evolutionDepth1Adapter
            rvPokemonEvolutionDepth2.recyclerView.adapter = evolutionDepth2Adapter
            rvPokemonEvolutionDepth3.recyclerView.adapter = evolutionDepth3Adapter
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}
