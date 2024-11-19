package poke.rogue.helper.presentation.dex.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import poke.rogue.helper.databinding.BottomSheetPokemonSortBinding
import poke.rogue.helper.presentation.dex.PokemonListActivity.Companion.SORT_RESULT_KEY
import poke.rogue.helper.presentation.util.parcelable
import poke.rogue.helper.presentation.util.repeatOnStarted

class PokemonSortBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetPokemonSortBinding? = null
    private val binding get() = requireNotNull(_binding)
    private lateinit var pokeSortAdapter: PokeSortAdapter
    private val viewModel by viewModels<PokeSortViewModel> {
        SavedStateViewModelFactory(
            requireActivity().application,
            this,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetPokemonSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        if (arguments != null && savedInstanceState == null) {
            val args = requireNotNull(argsFrom(requireArguments()))
            viewModel.init(args)
        }
        initAdapter()
        observeUiState()
        observeEvents()
    }

    private fun initAdapter() {
        pokeSortAdapter = PokeSortAdapter(viewModel)
        binding.rvPokemonSort.adapter = pokeSortAdapter
    }

    private fun observeUiState() {
        repeatOnStarted {
            viewModel.uiState.collect { state ->
                pokeSortAdapter.submitList(state.pokemonSorts)
            }
        }
    }

    private fun observeEvents() {
        repeatOnStarted {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is PokeSortUiEvent.CloseSort -> dismiss()
                    is PokeSortUiEvent.ApplySorting -> {
                        setFragmentResult(
                            SORT_RESULT_KEY,
                            bundleOf(ARGS_KEY to event.sort),
                        )
                        dismiss()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG: String = PokemonSortBottomSheetFragment::class.java.simpleName
        private const val ARGS_KEY = "PokemonSortBottomSheetFragment_args_key"

        fun argsFrom(result: Bundle): PokemonSortUiModel? {
            return result.parcelable<PokemonSortUiModel>(ARGS_KEY)
        }

        fun newInstance(sort: PokemonSortUiModel): PokemonSortBottomSheetFragment {
            return PokemonSortBottomSheetFragment().apply {
                arguments =
                    bundleOf(ARGS_KEY to sort)
            }
        }
    }
}
