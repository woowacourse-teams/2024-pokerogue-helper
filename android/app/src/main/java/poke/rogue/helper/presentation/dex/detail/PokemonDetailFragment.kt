package poke.rogue.helper.presentation.dex.detail

import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout.LayoutParams
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultDexRepository
import poke.rogue.helper.databinding.FragmentPokemonDetailBinding
import poke.rogue.helper.presentation.ability.detail.AbilityDetailFragment
import poke.rogue.helper.presentation.dex.PokemonStatAdapter
import poke.rogue.helper.presentation.dex.PokemonTypesAdapter
import poke.rogue.helper.presentation.toolbar.ToolbarFragment
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.context.colorOf
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
            spacingBetweenTypes = 0.dp,
        )
    }

    companion object {
        private const val POKEMON_ID = "pokemonId"
        private const val CONTAINER_ID = "containerId"
        private const val INVALID_CONTAINER_ID = -1
        val TAG: String = PokemonDetailFragment::class.java.simpleName

        private val typesUiConfig =
            TypeChip.PokemonTypeViewConfiguration(
                width = LayoutParams.WRAP_CONTENT,
                nameSize = 16.dp,
                iconSize = 20.dp,
                hasBackGround = false,
            )

        fun bundleOf(
            pokemonId: Long,
            containerId: Int,
        ) = Bundle().apply {
            putLong(POKEMON_ID, pokemonId)
            putInt(CONTAINER_ID, containerId)
        }

        @JvmStatic
        @BindingAdapter("progressColor")
        fun ProgressBar.setProgressDrawable(color: Int) {
            val background =
                GradientDrawable().apply {
                    setColor(context.colorOf(R.color.poke_grey_20))
                    cornerRadius = resources.getDimension(R.dimen.progress_bar_corner_radius)
                }

            val progress =
                GradientDrawable().apply {
                    setColor(context.colorOf(color))
                    cornerRadius = resources.getDimension(R.dimen.progress_bar_corner_radius)
                }

            val clipDrawable = ClipDrawable(progress, Gravity.START, ClipDrawable.HORIZONTAL)

            val layerDrawable =
                LayerDrawable(arrayOf(background, clipDrawable)).apply {
                    setId(0, android.R.id.background)
                    setId(1, android.R.id.progress)
                }

            progressDrawable = layerDrawable
        }
    }
}
