package poke.rogue.helper.presentation.dex.filter

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
import poke.rogue.helper.R
import poke.rogue.helper.databinding.BottomSheetPokemonFilterBinding
import poke.rogue.helper.presentation.dex.PokemonListActivity.Companion.FILTER_RESULT_KEY
import poke.rogue.helper.presentation.type.model.TypeUiModel1
import poke.rogue.helper.presentation.util.fragment.stringOf
import poke.rogue.helper.presentation.util.parcelable
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.ui.component.PokeChip

class PokemonFilterBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetPokemonFilterBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel by viewModels<PokeFilterViewModel> {
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
        _binding = BottomSheetPokemonFilterBinding.inflate(inflater, container, false)
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

        observeUiState()
        observeEvents()
    }

    private fun observeUiState() {
        repeatOnStarted {
            viewModel.uiState.collect {
                binding.chipGroupPokeFilterType.submitList(
                    it.types.map { selectableType ->
                        PokeChip.Spec(
                            selectableType.id,
                            "",
                            leadingIconRes = selectableType.data.typeIconResId,
                            sizes =
                                PokeChip.Sizes(
                                    leadingIconSize = 28.dp,
                                ),
                            colors =
                                PokeChip.Colors(
                                    selectedContainerColor = selectableType.data.typeColor,
                                ),
                            isSelected = selectableType.isSelected,
                            onSelect = viewModel::selectType,
                        )
                    },
                )
                binding.chipGroupPokeFilterGeneration.submitList(
                    it.generations.map { selectableGeneration ->
                        val generationText =
                            if (selectableGeneration.data == PokeGenerationUiModel.ALL) {
                                stringOf(R.string.dex_filter_all_generations)
                            } else {
                                stringOf(
                                    R.string.dex_filter_generation_format,
                                    selectableGeneration.data.number,
                                )
                            }
                        PokeChip.Spec(
                            selectableGeneration.id,
                            generationText,
                            isSelected = selectableGeneration.isSelected,
                            onSelect = viewModel::toggleGeneration,
                        )
                    },
                )
            }
        }
    }

    private fun observeEvents() {
        repeatOnStarted {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is PokeFilterUiEvent.CloseFilter -> dismiss()
                    is PokeFilterUiEvent.ApplyFiltering -> {
                        val args = PokeFilterUiModel(event.selectedTypes, event.generation)
                        setFragmentResult(
                            FILTER_RESULT_KEY,
                            bundleOf(ARGS_KEY to args),
                        )
                        dismiss()
                    }

                    is PokeFilterUiEvent.IDLE -> Unit
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
        val TAG: String = PokemonFilterBottomSheetFragment::class.java.simpleName
        private const val ARGS_KEY = "PokemonFilterBottomSheetFragment_args_key"

        fun argsFrom(result: Bundle): PokeFilterUiModel? {
            return result.parcelable<PokeFilterUiModel>(ARGS_KEY)
        }

        fun newInstance(
            selectedTypes: List<TypeUiModel1> = emptyList(),
            selectedGeneration: PokeGenerationUiModel = PokeGenerationUiModel.ALL,
        ): PokemonFilterBottomSheetFragment {
            return PokemonFilterBottomSheetFragment().apply {
                arguments =
                    bundleOf(ARGS_KEY to PokeFilterUiModel(selectedTypes, selectedGeneration))
            }
        }
    }
}
