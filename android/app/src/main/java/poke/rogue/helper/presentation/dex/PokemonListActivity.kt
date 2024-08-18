package poke.rogue.helper.presentation.dex

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import poke.rogue.helper.R
import poke.rogue.helper.data.model.Ability
import poke.rogue.helper.data.model.Pokemon
import poke.rogue.helper.data.model.PokemonDetail
import poke.rogue.helper.data.model.PokemonFilter
import poke.rogue.helper.data.model.PokemonGeneration
import poke.rogue.helper.data.model.PokemonSkill
import poke.rogue.helper.data.model.PokemonSort
import poke.rogue.helper.data.model.Stat
import poke.rogue.helper.data.model.Type
import poke.rogue.helper.data.repository.DexRepository
import poke.rogue.helper.databinding.ActivityPokemonListBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleActivity
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.dex.detail.PokemonDetailActivity
import poke.rogue.helper.presentation.dex.filter.PokeFilterUiModel
import poke.rogue.helper.presentation.dex.filter.PokemonFilterBottomSheetFragment
import poke.rogue.helper.presentation.dex.sort.PokemonSortBottomSheetFragment
import poke.rogue.helper.presentation.dex.sort.PokemonSortUiModel
import poke.rogue.helper.presentation.util.activity.hideKeyboard
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.ui.component.PokeChip
import poke.rogue.helper.ui.component.PokeChip.Companion.bindPokeChip
import poke.rogue.helper.ui.layout.PaddingValues

class PokemonListActivity :
    ErrorHandleActivity<ActivityPokemonListBinding>(R.layout.activity_pokemon_list) {
    private val viewModel by viewModels<PokemonListViewModel> {
        PokemonListViewModel.factory(
//            DefaultDexRepository.instance(),
            FakeDexRepository(),
        )
    }
    override val errorViewModel: ErrorHandleViewModel
        get() = viewModel

    private val pokemonAdapter: PokemonAdapter by lazy {
        PokemonAdapter(viewModel)
    }

    override val toolbar: Toolbar
        get() = binding.toolbarDex

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        initAdapter()
        initObservers()
        initListeners()
    }

    private fun initAdapter() {
        binding.rvPokemonList.apply {
            val spanCount =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
            adapter = pokemonAdapter
            layoutManager = GridLayoutManager(context, spanCount)
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount = spanCount,
                    spacing = 9.dp,
                    includeEdge = false,
                ),
            )
        }
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.uiState.collect { uiState ->
                pokemonAdapter.submitList(uiState.pokemons)
                binding.chipPokeFiter.bindPokeChip(
                    PokeChip.Spec(
                        label =
                            stringOf(
                                R.string.dex_filter_chip,
                                if (uiState.isFiltered) uiState.filterCount.toString() else "",
                            ),
                        trailingIconRes = R.drawable.ic_filter,
                        isSelected = uiState.isFiltered,
                        padding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                        onSelect = {
                            PokemonFilterBottomSheetFragment.newInstance(
                                uiState.filteredTypes,
                                uiState.filteredGeneration,
                            ).show(
                                supportFragmentManager,
                                PokemonFilterBottomSheetFragment.TAG,
                            )
                        },
                    ),
                )
                binding.chipPokeSort.bindPokeChip(
                    PokeChip.Spec(
                        label = uiState.sort.label.clean(),
                        trailingIconRes = R.drawable.ic_sort,
                        isSelected = uiState.isSorted,
                        padding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                        onSelect = {
                            PokemonSortBottomSheetFragment.newInstance(uiState.sort).show(
                                supportFragmentManager,
                                PokemonSortBottomSheetFragment.TAG,
                            )
                        },
                    ),
                )
            }
        }
        repeatOnStarted {
            viewModel.navigateToDetailEvent.collect { pokemonId ->
                hideKeyboard()
                startActivity(PokemonDetailActivity.intent(this, pokemonId))
            }
        }
        val fm: FragmentManager = supportFragmentManager

        fm.setFragmentResultListener(FILTER_RESULT_KEY, this) { key, bundle ->
            val filterArgs: PokeFilterUiModel =
                PokemonFilterBottomSheetFragment.argsFrom(bundle)
                    ?: return@setFragmentResultListener
            viewModel.filterPokemon(filterArgs)
        }
        fm.setFragmentResultListener(SORT_RESULT_KEY, this) { key, bundle ->
            val sortArgs: PokemonSortUiModel =
                PokemonSortBottomSheetFragment.argsFrom(bundle)
                    ?: return@setFragmentResultListener
            viewModel.sortPokemon(sortArgs)
        }
    }

    private fun initListeners() {
        binding.root.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun String.clean() =
        this
            .replace("\\s".toRegex(), "")
            .replace("[^a-zA-Z0-9ㄱ-ㅎ가-힣]".toRegex(), "")

    companion object {
        const val FILTER_RESULT_KEY = "FILTER_RESULT_KEY_result_key"
        const val SORT_RESULT_KEY = "SORT_RESULT_KEY_result_key"
    }
}

// TODO : Remove this class
class FakeDexRepository : DexRepository {
    override suspend fun pokemons(): List<Pokemon> = POKEMONS

    override suspend fun filteredPokemons(
        name: String,
        sort: PokemonSort,
        filters: List<PokemonFilter>,
    ): List<Pokemon> {
        return if (name.isEmpty()) {
            pokemons()
        } else {
            pokemons().filter { it.name.contains(name) }
        }.toFilteredPokemons(sort, filters)
    }

    override suspend fun pokemonDetail(id: Long): PokemonDetail = DUMMY_POKEMON_DETAIL

    private fun List<Pokemon>.toFilteredPokemons(
        sort: PokemonSort,
        filters: List<PokemonFilter>,
    ): List<Pokemon> {
        return this
            .filter { pokemon ->
                filters.all { filter ->
                    when (filter) {
                        is PokemonFilter.ByType -> pokemon.types.contains(filter.type)
                        is PokemonFilter.ByGeneration -> pokemon.generation == filter.generation
                    }
                }
            }
            .sortedWith(sort)
    }

    companion object {
        private const val FORMAT_POKEMON_IMAGE_URL =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other" +
                "/official-artwork/"

        private const val POSTFIX_PNG = ".png"

        private fun pokemonImageUrl(pokemonId: Long) = FORMAT_POKEMON_IMAGE_URL + pokemonId + POSTFIX_PNG

        val POKEMONS: List<Pokemon> =
            listOf(
                Pokemon(
                    id = 1,
                    dexNumber = 1,
                    name = "이상해씨",
                    imageUrl = pokemonImageUrl(pokemonId = 1),
                    types = listOf(Type.GRASS, Type.POISON),
                    generation = PokemonGeneration.ONE,
                    baseStat = 318,
                    hp = 45,
                    attack = 49,
                    defense = 49,
                    specialAttack = 65,
                    specialDefense = 65,
                    speed = 45,
                ),
                Pokemon(
                    id = 2,
                    dexNumber = 2,
                    name = "이상해풀",
                    imageUrl = pokemonImageUrl(pokemonId = 2),
                    types = listOf(Type.GRASS, Type.POISON),
                    generation = PokemonGeneration.ONE,
                    baseStat = 405,
                    hp = 60,
                    attack = 62,
                    defense = 63,
                    specialAttack = 80,
                    specialDefense = 80,
                    speed = 60,
                ),
                Pokemon(
                    id = 3,
                    dexNumber = 3,
                    name = "이상해꽃",
                    imageUrl = pokemonImageUrl(pokemonId = 3),
                    types = listOf(Type.GRASS, Type.POISON),
                    generation = PokemonGeneration.ONE,
                    baseStat = 525,
                    hp = 80,
                    attack = 82,
                    defense = 83,
                    specialAttack = 100,
                    specialDefense = 100,
                    speed = 195,
                ),
                Pokemon(
                    id = 4,
                    dexNumber = 4,
                    name = "파이리",
                    imageUrl = pokemonImageUrl(pokemonId = 4),
                    types = listOf(Type.FIRE),
                    generation = PokemonGeneration.ONE,
                    baseStat = 309,
                    hp = 39,
                    attack = 52,
                    defense = 43,
                    specialAttack = 60,
                    specialDefense = 50,
                    speed = 65,
                ),
                Pokemon(
                    id = 5,
                    dexNumber = 5,
                    name = "리자드",
                    imageUrl = pokemonImageUrl(pokemonId = 5),
                    types = listOf(Type.FIRE),
                    generation = PokemonGeneration.ONE,
                    baseStat = 405,
                    hp = 58,
                    attack = 64,
                    defense = 58,
                    specialAttack = 80,
                    specialDefense = 65,
                    speed = 80,
                ),
                Pokemon(
                    id = 6,
                    dexNumber = 6,
                    name = "리자몽",
                    imageUrl = pokemonImageUrl(pokemonId = 6),
                    types = listOf(Type.FIRE, Type.FLYING),
                    generation = PokemonGeneration.ONE,
                    baseStat = 534,
                    hp = 78,
                    attack = 84,
                    defense = 78,
                    specialAttack = 109,
                    specialDefense = 85,
                    speed = 100,
                ),
                Pokemon(
                    id = 7,
                    dexNumber = 7,
                    name = "꼬부기",
                    imageUrl = pokemonImageUrl(pokemonId = 7),
                    types = listOf(Type.WATER),
                    generation = PokemonGeneration.ONE,
                    baseStat = 314,
                    hp = 44,
                    attack = 48,
                    defense = 65,
                    specialAttack = 50,
                    specialDefense = 64,
                    speed = 43,
                ),
                Pokemon(
                    id = 8,
                    dexNumber = 8,
                    name = "어니부기",
                    imageUrl = pokemonImageUrl(pokemonId = 8),
                    types = listOf(Type.WATER),
                    generation = PokemonGeneration.ONE,
                    baseStat = 405,
                    hp = 59,
                    attack = 63,
                    defense = 80,
                    specialAttack = 65,
                    specialDefense = 80,
                    speed = 58,
                ),
                Pokemon(
                    id = 9,
                    dexNumber = 9,
                    name = "거북왕",
                    imageUrl = pokemonImageUrl(pokemonId = 9),
                    types = listOf(Type.WATER),
                    generation = PokemonGeneration.ONE,
                    baseStat = 530,
                    hp = 79,
                    attack = 83,
                    defense = 100,
                    specialAttack = 85,
                    specialDefense = 105,
                    speed = 78,
                ),
                Pokemon(
                    id = 373,
                    dexNumber = 373,
                    name = "보만다",
                    imageUrl = pokemonImageUrl(pokemonId = 373),
                    types = listOf(Type.FLYING, Type.DRAGON),
                    generation = PokemonGeneration.THREE,
                    baseStat = 600,
                    hp = 95,
                    attack = 135,
                    defense = 80,
                    specialAttack = 110,
                    specialDefense = 80,
                    speed = 100,
                ),
            )

        val DUMMY_POKEMON_DETAIL =
            PokemonDetail(
                pokemon = Pokemon.DUMMY,
                stats =
                    listOf(
                        Stat("hp", 45),
                        Stat("attack", 49),
                        Stat("defense", 49),
                        Stat("specialAttack", 65),
                        Stat("specialDefense", 65),
                        Stat("speed", 45),
                        Stat("total", 318),
                    ),
                abilities =
                    listOf(
                        Ability(450, "심록", description = "HP가 줄었을 때 풀타입 기술의 위력이 올라간다."),
                        Ability(419, "엽록소", description = "날씨가 맑을 때 스피드가 올라간다."),
                    ),
                skills = PokemonSkill.FAKE_SKILLS,
                height = 0.7f,
                weight = 6.9f,
            )
    }
}
