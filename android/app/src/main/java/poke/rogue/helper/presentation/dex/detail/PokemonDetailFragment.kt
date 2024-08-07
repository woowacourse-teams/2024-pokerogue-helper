package poke.rogue.helper.presentation.dex.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultDexRepository
import poke.rogue.helper.databinding.FragmentPokemonDetailBinding
import poke.rogue.helper.presentation.ability.detail.AbilityDetailFragment
import poke.rogue.helper.presentation.dex.PokemonStatAdapter
import poke.rogue.helper.presentation.dex.PokemonTypesAdapter
import poke.rogue.helper.presentation.error.ErrorEvent
import poke.rogue.helper.presentation.error.NetworkErrorActivity
import poke.rogue.helper.presentation.toolbar.ToolbarFragment
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.fragment.startActivity
import poke.rogue.helper.presentation.util.fragment.stringOf
import poke.rogue.helper.presentation.util.fragment.toast
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.LinearSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.setImage

class PokemonDetailFragment :
    ToolbarFragment<FragmentPokemonDetailBinding>(R.layout.fragment_pokemon_detail) {
    private val viewModel by viewModels<PokemonDetailViewModel> {
        PokemonDetailViewModel.factory(DefaultDexRepository.instance())
    }
    private val abilityAdapter by lazy { AbilityTitleAdapter(viewModel) }
    private val pokemonStatAdapter by lazy { PokemonStatAdapter() }
    private lateinit var pokemonTypesAdapter: PokemonTypesAdapter

    override val toolbar: Toolbar
        get() = binding.toolbarDexDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updatePokemonDetail(arguments?.getLong(POKEMON_ID))
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        pokemonTypesAdapter =
            PokemonTypesAdapter(
                context = requireContext(),
                viewGroup = binding.layoutPokemonDetailPokemonTypes,
            )
        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        with(binding) {
            rvStatList.adapter = pokemonStatAdapter
            rvPokemonAbilities.adapter = abilityAdapter
            rvPokemonAbilities.addItemDecoration(
                LinearSpacingItemDecoration(
                    spacing = 7.dp,
                    includeEdge = false,
                    orientation = LinearSpacingItemDecoration.Orientation.HORIZONTAL,
                ),
            )
        }
    }

    private fun initObservers() {
        observePokemonDetailUi()
        observeNavigateToAbilityDetailEvent()
        repeatOnStarted {
            viewModel.commonErrorEvent.collect {
                when (it) {
                    is ErrorEvent.NetworkConnection -> startActivity<NetworkErrorActivity>()
                    is ErrorEvent.UnknownError, is ErrorEvent.HttpException -> {
                        toast(it.msg ?: getString(R.string.error_IO_Exception))
                    }
                }
            }
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

    private fun observeNavigateToAbilityDetailEvent() {
        repeatOnStarted {
            viewModel.navigationToDetailEvent.collect { abilityId ->
                parentFragmentManager.commit {
                    val containerId = arguments?.getInt(CONTAINER_ID) ?: INVALID_CONTAINER_ID
                    if (containerId == INVALID_CONTAINER_ID) {
                        toast(R.string.dex_detail_error_containerId)
                        return@commit
                    }
                    replace<AbilityDetailFragment>(
                        containerId,
                        args = AbilityDetailFragment.bundleOf(abilityId, containerId),
                    )
                    addToBackStack(TAG)
                }
            }
        }
    }

    private fun bindPokemonDetail(pokemonDetail: PokemonDetailUiState.Success) {
        with(binding) {
            ivPokemonDetailPokemon.setImage(pokemonDetail.pokemon.imageUrl)

            tvPokemonDetailPokemonName.text =
                stringOf(
                    R.string.dex_poke_name_format,
                    pokemonDetail.pokemon.name,
                    pokemonDetail.pokemon.dexNumber,
                )
        }

        pokemonStatAdapter.submitList(pokemonDetail.stats)
        abilityAdapter.submitList(pokemonDetail.abilities)
        pokemonTypesAdapter.addTypes(
            types = pokemonDetail.pokemon.types,
            config = typesUiConfig,
            spacingBetweenTypes = 10.dp,
        )
    }

    companion object {
        private const val POKEMON_ID = "pokemonId"
        private const val CONTAINER_ID = "containerId"
        private const val INVALID_CONTAINER_ID = -1
        val TAG: String = PokemonDetailFragment::class.java.simpleName

        private val typesUiConfig =
            TypeChip.PokemonTypeViewConfiguration(
                nameSize = 17.dp,
                iconSize = 30.dp,
                hasBackGround = true,
            )

        fun bundleOf(
            pokemonId: Long,
            containerId: Int,
        ) = Bundle().apply {
            putLong(POKEMON_ID, pokemonId)
            putInt(CONTAINER_ID, containerId)
        }
    }
}
