package poke.rogue.helper.presentation.ability.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import poke.rogue.helper.R
import poke.rogue.helper.databinding.FragmentAbilityDetailBinding
import poke.rogue.helper.presentation.ability.model.toUi
import poke.rogue.helper.presentation.base.error.ErrorEvent
import poke.rogue.helper.presentation.base.error.NetworkErrorActivity
import poke.rogue.helper.presentation.base.toolbar.ToolbarFragment
import poke.rogue.helper.presentation.dex.detail.PokemonDetailActivity
import poke.rogue.helper.presentation.home.HomeActivity
import poke.rogue.helper.presentation.util.fragment.startActivity
import poke.rogue.helper.presentation.util.fragment.toast
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class AbilityDetailFragment :
    ToolbarFragment<FragmentAbilityDetailBinding>(R.layout.fragment_ability_detail) {
    private val viewModel by viewModel<AbilityDetailViewModel>()

    private val adapter: AbilityDetailAdapter by lazy { AbilityDetailAdapter(viewModel) }

    override val toolbar: Toolbar
        get() = binding.toolbarAbilityDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val abilityId = arguments?.getString(ABILITY_ID) ?: ""
        viewModel.updateAbilityDetail(abilityId)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
        initObservers()
    }

    private fun initView() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
    }

    private fun initAdapter() {
        val decoration = GridSpacingItemDecoration(3, 9.dp, false)
        binding.rvAbilityDetailPokemon.adapter = adapter
        binding.rvAbilityDetailPokemon.addItemDecoration(decoration)
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.abilityDetail.collect { abilityDetail ->
                when (abilityDetail) {
                    is AbilityDetailUiState.Loading -> {}
                    is AbilityDetailUiState.Success -> {
                        binding.abilityUiModel = abilityDetail.data.toUi()
                        adapter.submitList(abilityDetail.data.pokemons)
                    }
                }
            }
        }

        repeatOnStarted {
            viewModel.commonErrorEvent.collect {
                when (it) {
                    is ErrorEvent.NetworkException -> startActivity<NetworkErrorActivity>()
                    is ErrorEvent.UnknownError, is ErrorEvent.HttpException -> {
                        toast(it.msg ?: getString(R.string.error_IO_Exception))
                    }
                }
            }
        }

        repeatOnStarted {
            viewModel.errorEvent.collect {
                toast(R.string.ability_detail_error_abilityId)
            }
        }

        repeatOnStarted {
            viewModel.navigationToPokemonDetailEvent.collect { pokemonId ->
                PokemonDetailActivity.intent(requireContext(), pokemonId).let(::startActivity)
            }
        }

        repeatOnStarted {
            viewModel.navigateToHomeEvent.collect {
                if (it) {
                    startActivity(HomeActivity.intent(requireContext()))
                }
            }
        }
    }

    companion object {
        private const val ABILITY_ID = "abilityId"
        private val TAG = AbilityDetailFragment::class.java.simpleName

        fun bundleOf(abilityId: String) =
            Bundle().apply {
                putString(ABILITY_ID, abilityId)
            }
    }
}
