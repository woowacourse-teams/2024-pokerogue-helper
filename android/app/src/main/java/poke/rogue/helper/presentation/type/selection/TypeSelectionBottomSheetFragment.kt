package poke.rogue.helper.presentation.type.selection

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import poke.rogue.helper.databinding.FragmentTypeSelectionBottomSheetBinding
import poke.rogue.helper.presentation.type.TypeEvent
import poke.rogue.helper.presentation.type.TypeViewModel
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel1
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
    private val disabledTypes: Set<TypeUiModel1> by lazy {
        arguments?.serializable<Array<TypeUiModel1>>(KEY_DISABLED_TYPES)?.toSet()
            ?: error("Invalid DisabledTypes")
    }

    private val sharedViewModel by activityViewModels<TypeViewModel>()

    private val typeSelectionAdapter by lazy {
        TypeSelectionAdapter(
            sharedViewModel.allTypes,
            selectorType,
            disabledTypes,
            sharedViewModel,
        )
    }

    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
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
        with(binding.rvTypeSelection) {
            adapter = typeSelectionAdapter

            val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 6 else 4
            val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
            layoutManager = gridLayoutManager

            val decoration = GridSpacingItemDecoration(spanCount = spanCount, spacing = 20.dp, includeEdge = false)
            addItemDecoration(decoration)
        }
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
            disabledTypes: Set<TypeUiModel1>,
        ): TypeSelectionBottomSheetFragment {
            return TypeSelectionBottomSheetFragment().withArgs {
                putSerializable(KEY_SELECTOR_TYPE, selectorType)
                putSerializable(KEY_DISABLED_TYPES, disabledTypes.toTypedArray())
            }
        }
    }
}
