package poke.rogue.helper.presentation.dex.detail

import android.os.Bundle
import android.widget.LinearLayout.LayoutParams
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultDexRepository
import poke.rogue.helper.databinding.ActivityPokemonDetailBinding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.dex.PokemonTypesAdapter
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.loadImageWithProgress
import timber.log.Timber

class PokemonDetailActivity : BindingActivity<ActivityPokemonDetailBinding>(R.layout.activity_pokemon_detail) {
    private val viewModel by viewModels<PokemonDetailViewModel> {
        PokemonDetailViewModel.factory(DefaultDexRepository.instance())
    }

    private lateinit var pokemonTypesAdapter: PokemonTypesAdapter
    private lateinit var pokemonDetailPagerAdapter: PokemonDetailPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updatePokemonDetail(intent.getLongExtra(POKEMON_ID, 2606))

        initAdapter()
        initObservers()
    }

    private fun initAdapter()  {
        pokemonTypesAdapter =
            PokemonTypesAdapter(
                context = this,
                viewGroup = binding.layoutPokemonDetailPokemonTypes,
            )

        pokemonDetailPagerAdapter = PokemonDetailPagerAdapter(this)
        binding.pagerPokemonDetail.apply {
            adapter = pokemonDetailPagerAdapter
        }

        TabLayoutMediator(binding.tabLayoutPokemonDetail, binding.pagerPokemonDetail) { tab, position ->
            Timber.d("ViewPager position: $position")
            when (position) {
                0 -> tab.text = "능력치"
                1 -> tab.text = "진화 정보"
                2 -> tab.text = "기술 정보"
                3 -> tab.text = "정보"
            }
        }.attach()
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.uiState.collect { pokemonDetail ->
                when (pokemonDetail) {
                    is PokemonDetailUiState.IsLoading -> return@collect
                    is PokemonDetailUiState.Success -> {
                        bindPokemonDetail(pokemonDetail)
                    }
                }
            }
        }
    }

    private fun bindPokemonDetail(pokemonDetail: PokemonDetailUiState.Success) {
        with(binding) {
            ivPokemonDetailPokemon.loadImageWithProgress(pokemonDetail.pokemon.imageUrl, progressIndicatorPokemonDetail)

            tvPokemonDetailPokemonName.text =
                stringOf(
                    R.string.dex_poke_name_format,
                    pokemonDetail.pokemon.name,
                    pokemonDetail.pokemon.dexNumber,
                )
        }

        pokemonTypesAdapter.addTypes(
            types = pokemonDetail.pokemon.types,
            config = typesUiConfig,
            spacingBetweenTypes = 0.dp,
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
