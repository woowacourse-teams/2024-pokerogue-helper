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
import poke.rogue.helper.presentation.biome.detail.BiomeDetailActivity
import poke.rogue.helper.presentation.dex.PokemonTypesAdapter
import poke.rogue.helper.presentation.home.HomeActivity
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.context.stringArrayOf
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.loadImageWithProgress
import timber.log.Timber

class PokemonDetailActivity :
    ToolbarActivity<ActivityPokemonDetailBinding>(R.layout.activity_pokemon_detail) {
    private val viewModel by viewModels<PokemonDetailViewModel> {
        PokemonDetailViewModel.factory(DefaultDexRepository.instance())
    }

    private lateinit var pokemonTypesAdapter: PokemonTypesAdapter
    private lateinit var pokemonDetailPagerAdapter: PokemonDetailPagerAdapter
    private lateinit var floatingButtonHandler: FloatingButtonHandler

    override val toolbar: Toolbar
        get() = binding.toolbarPokemonDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updatePokemonDetail(intent.getStringExtra(POKEMON_ID).toString())

        binding.eventHandler = viewModel
        binding.lifecycleOwner = this
        binding.vm = viewModel

        floatingButtonHandler = FloatingButtonHandler(
            this,
            binding.fabPokemonDetailBattle,
            binding.efabPokemonDetailBattleWithMine,
            binding.efabPokemonDetailBattleWithOpponent
        )

        initAdapter()
        initObservers()
        initFloatingActionButton()
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

        val tabTitles = stringArrayOf(R.array.pokemon_detail_tab_titles)
        TabLayoutMediator(binding.tabLayoutPokemonDetail, binding.pagerPokemonDetail) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun initObservers() {
        observePokemonDetailUi()
        observeNavigateToHomeEvent()
        observeNavigateToAbilityDetailEvent()
        observeNavigateToBiomeDetailEvent()
        observeNavigateToPokemonDetailEvent()
        observeNavigateToBattleEvent()
    }

    private fun initFloatingActionButton() {
        binding.fabPokemonDetailBattle.setOnClickListener {
            floatingButtonHandler.toggleFab()
        }
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
            viewModel.navigationToAbilityDetailEvent.collect { abilityId ->
                startActivity(AbilityActivity.intent(this, abilityId))
            }
        }
    }

    private fun observeNavigateToBiomeDetailEvent() {
        repeatOnStarted {
            viewModel.navigationToBiomeDetailEvent.collect { biomeId ->
                startActivity(BiomeDetailActivity.intent(this, biomeId))
            }
        }
    }

    private fun observeNavigateToPokemonDetailEvent() {
        repeatOnStarted {
            viewModel.navigateToPokemonDetailEvent.collect { pokemonId ->
                startActivity(intent(this, pokemonId))
            }
        }
    }

    // TODO: 예니 여기서 하면 될 Battle Activity 로 이동하면 될 것 같아요
    private fun observeNavigateToBattleEvent() {
        repeatOnStarted {
            viewModel.navigateToPokemonDetailToBattleEvent.collect { battleEvent ->
                when (battleEvent.battlePopUp) {
                    is MyPokemon -> {
                        Timber.d("내 포켓몬으로 배틀 액티비티로 이동 pokemon: ${battleEvent.pokemon}")
                        // TODO()
                    }

                    is EnemyPokemon -> {
                        Timber.d("상대 포켓몬으로 배틀 액티비티로 이동 pokemon: ${battleEvent.pokemon}")
                        // TODO()
                    }
                }
            }
        }
    }

    private fun bindPokemonDetail(pokemonDetail: PokemonDetailUiState.Success) {
        with(binding) {
            ivPokemonDetailPokemon.loadImageWithProgress(
                pokemonDetail.pokemon.imageUrl,
                progressIndicatorPokemonDetail,
            )

            collapsingToolbarLayoutPokemonDetail.title =
                stringOf(
                    R.string.pokemon_list_poke_name_format,
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
            pokemonId: String,
        ): Intent =
            Intent(context, PokemonDetailActivity::class.java).apply {
                putExtra(POKEMON_ID, pokemonId)
            }
    }
}
