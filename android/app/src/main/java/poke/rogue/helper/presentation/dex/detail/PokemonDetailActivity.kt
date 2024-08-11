package poke.rogue.helper.presentation.dex.detail

import android.os.Bundle
import android.widget.LinearLayout.LayoutParams
import androidx.activity.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultDexRepository
import poke.rogue.helper.databinding.ActivityPokemonDetailBinding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.dex.PokemonTypesAdapter
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.loadImageWithProgress

class PokemonDetailActivity : BindingActivity<ActivityPokemonDetailBinding>(R.layout.activity_pokemon_detail) {
    private val viewModel by viewModels<PokemonDetailViewModel> {
        PokemonDetailViewModel.factory(DefaultDexRepository.instance())
    }

    private lateinit var pokemonTypesAdapter: PokemonTypesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updatePokemonDetail(intent.getLongExtra(POKEMON_ID, 1))

        pokemonTypesAdapter =
            PokemonTypesAdapter(
                context = this,
                viewGroup = binding.layoutPokemonDetailPokemonTypes,
            )

        binding.ivPokemonDetailPokemon.loadImageWithProgress(
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            progressIndicator = binding.progressIndicatorPokemonDetail,
        )

        initAdapter()
    }

    private fun initAdapter() {
        pokemonTypesAdapter.addTypes(
            listOf(
                TypeUiModel.GRASS,
                TypeUiModel.POISON,
            ),
            typesUiConfig,
        )
    }

    companion object {
        private const val POKEMON_ID = "pokemonId"
        val TAG: String = PokemonDetailActivity::class.java.simpleName

        private val typesUiConfig =
            TypeChip.PokemonTypeViewConfiguration(
                width = LayoutParams.WRAP_CONTENT,
                nameSize = 16.dp,
                iconSize = 20.dp,
                hasBackGround = false,
            )
    }
}
