package poke.rogue.helper.presentation.dex.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout.LayoutParams
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayoutMediator
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultDexRepository
import poke.rogue.helper.databinding.ActivityPokemonDetailBinding
import poke.rogue.helper.presentation.ability.AbilityActivity
import poke.rogue.helper.presentation.base.toolbar.ToolbarActivity
import poke.rogue.helper.presentation.dex.PokemonTypesAdapter
import poke.rogue.helper.presentation.home.HomeActivity
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.loadImageWithProgress

class PokemonDetailActivity :
    ToolbarActivity<ActivityPokemonDetailBinding>(R.layout.activity_pokemon_detail) {
    private val viewModel by viewModels<PokemonDetailViewModel> {
        PokemonDetailViewModel.factory(DefaultDexRepository.instance())
    }

    private lateinit var pokemonTypesAdapter: PokemonTypesAdapter
    private lateinit var pokemonDetailPagerAdapter: PokemonDetailPagerAdapter

    override val toolbar: Toolbar
        get() = binding.toolbarPokemonDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updatePokemonDetail(intent.getLongExtra(POKEMON_ID, 2606))

        binding.eventHandler = viewModel
        binding.lifecycleOwner = this

        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        pokemonTypesAdapter =
            PokemonTypesAdapter(
                context = this,
                viewGroup = binding.layoutPokemonDetailPokemonTypes,
            )

        pokemonDetailPagerAdapter = PokemonDetailPagerAdapter(this)
        binding.pagerPokemonDetail.apply {
            adapter = pokemonDetailPagerAdapter
        }

        TabLayoutMediator(
            binding.tabLayoutPokemonDetail,
            binding.pagerPokemonDetail,
        ) { tab, position ->
            when (position) {
                0 -> tab.text = stringOf(R.string.pokemon_detail_pokemon_stats_title)
                1 -> tab.text = stringOf(R.string.pokemon_detail_pokemon_evolution_title)
                2 -> tab.text = stringOf(R.string.pokemon_detail_pokemon_skills_title)
                3 -> tab.text = stringOf(R.string.pokemon_detail_pokemon_information_title)
            }
        }.attach()
    }

    private fun initObservers() {
        observePokemonDetailUi()
        observeNavigateToHomeEvent()
        observeNavigateToAbilityDetailEvent()
    }

    private fun observePokemonDetailUi() {
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

    private fun observeNavigateToHomeEvent() {
        repeatOnStarted {
            viewModel.navigateToHomeEvent.collect {
                if (it) {
                    startActivity(HomeActivity.intent(this))
                }
            }
        }
    }

    private fun observeNavigateToAbilityDetailEvent() {
        repeatOnStarted {
            viewModel.navigationToDetailEvent.collect { abilityId ->
                startActivity(AbilityActivity.intent(this, abilityId))
            }
        }
    }

    private fun bindPokemonDetail(pokemonDetail: PokemonDetailUiState.Success) {
        with(binding) {
            ivPokemonDetailPokemon.loadImageWithProgress(
                pokemonDetail.pokemon.imageUrl,
                progressIndicatorPokemonDetail,
            )

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

        fun intent(
            context: Context,
            pokemonId: Long,
        ): Intent =
            Intent(context, PokemonDetailActivity::class.java).apply {
                putExtra(POKEMON_ID, pokemonId)
            }
    }
}
