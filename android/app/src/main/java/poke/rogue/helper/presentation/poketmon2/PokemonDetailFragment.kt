package poke.rogue.helper.presentation.poketmon2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentPokemonDetailBinding
import poke.rogue.helper.presentation.base.BindingFragment
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import timber.log.Timber

class PokemonDetailFragment :
    BindingFragment<FragmentPokemonDetailBinding>(R.layout.fragment_pokemon_detail) {
    private val viewModel by viewModels<PokemonDetailViewModel>()
    private lateinit var pokemonTypeAdapter: PokemonTypeAdapter
    private lateinit var pokemonStatAdapter: PokemonStatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updatePokemonDetail(arguments?.getLong(POKEMON_ID))
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        binding.rvTypeList.apply {
            pokemonTypeAdapter = PokemonTypeAdapter()
            adapter = pokemonTypeAdapter
        }
        binding.rvStatList.apply {
            pokemonStatAdapter = PokemonStatAdapter()
            adapter = pokemonStatAdapter
        }
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.uiState.collect {
                pokemonTypeAdapter.submitList(it.types)
                pokemonStatAdapter.submitList(it.stats)
            }
        }
    }

    companion object {
        private const val POKEMON_ID = "pokemonId"
        fun bundleOf(pokemonId: Long) = Bundle().apply {
            putLong(POKEMON_ID, pokemonId)
        }
    }
}

class PokemonDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PokemonDetailUiState.DUMMY)
    val uiState = _uiState.asStateFlow()

    fun updatePokemonDetail(pokemonId: Long?) {
        Timber.d("updatePokemonDetail: $pokemonId")
        if (pokemonId == null) {
            _uiState.value = PokemonDetailUiState.DUMMY
            return
        }
        // TODO : pokemonID 에 해당하는 포켓몬 정보를 가져와서 _uiState 에 업데이트
        _uiState.value = PokemonDetailUiState.DUMMY
    }
}

data class Stat(
    val name: String,
    val amount: Int,
    val limit: Int,
) {
    val progress: Int
        get() = amount * 100 / limit
}

data class PokemonDetailUiState(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val height: Float,
    val weight: Float,
    val types: List<TypeUiModel>,
    val stats: List<Stat>,
) {
    companion object {
        val DUMMY = PokemonDetailUiState(
            id = 6,
            name = "리자몽",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/6.png",
            height = 1.7f,
            weight = 90.5f,
            types = listOf(TypeUiModel.FIRE, TypeUiModel.FLYING),
            stats = listOf(
                Stat("HP", 168, 300),
                Stat("ATK", 205, 300),
                Stat("DEF", 64, 300),
                Stat("SPD", 204, 300),
            )
        )
    }
}