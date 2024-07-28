package poke.rogue.helper.presentation.type.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import poke.rogue.helper.databinding.FragmentTypeSelectionBottomSheetBinding
import poke.rogue.helper.presentation.type.TypeEvent
import poke.rogue.helper.presentation.type.TypeViewModel
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.util.fragment.withArgs
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.serializable
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class TypeSelectionBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTypeSelectionBottomSheetBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val selectorType: SelectorType by lazy {
        arguments?.serializable<SelectorType>(KEY_SELECTOR_TYPE)
            ?: error("InValid TypeSelector")
    }
    private val disabledTypes: Set<TypeUiModel> by lazy {
        arguments?.serializable<Array<TypeUiModel>>(KEY_DISABLED_TYPES)?.toSet()
            ?: error("Invalid DisabledTypes")
    }

    private val sharedViewModel by activityViewModels<TypeViewModel>()

    private val adapter by lazy {
        TypeSelectionAdapter(
            sharedViewModel.allTypes,
            selectorType,
            disabledTypes,
            sharedViewModel,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTypeSelectionBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObserver()
    }

    private fun initAdapter() {
        binding.rvTypeSelection.adapter = adapter
        val decoration = GridSpacingItemDecoration(spanCount = 4, spacing = 20.dp, includeEdge = false)
        binding.rvTypeSelection.addItemDecoration(decoration)
    }

    private fun initObserver() {
        repeatOnStarted {
            sharedViewModel.typeEvent.collect {
                if (it is TypeEvent.HideSelection) {
                    dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "type_selection_bottom_sheet_fragment"
        private const val KEY_SELECTOR_TYPE = "selection_type"
        private const val KEY_DISABLED_TYPES = "disabled_type"

        fun newInstance(
            selectorType: SelectorType,
            disabledTypes: Set<TypeUiModel>,
        ): TypeSelectionBottomSheetFragment {
            return TypeSelectionBottomSheetFragment().withArgs {
                putSerializable(KEY_SELECTOR_TYPE, selectorType)
                putSerializable(KEY_DISABLED_TYPES, disabledTypes.toTypedArray())
            }
        }
    }
}
