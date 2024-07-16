package poke.rogue.helper.presentation.type.typeselection

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import poke.rogue.helper.databinding.FragmentTypeChoiceBottomSheetBinding
import poke.rogue.helper.local.DummyTypeData
import poke.rogue.helper.presentation.type.TypeViewModel
import poke.rogue.helper.presentation.type.model.SelectorType
import poke.rogue.helper.presentation.type.model.TypeUiModel.Companion.toUiModel
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class TypeSelectionBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTypeChoiceBottomSheetBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val selectorType: SelectorType by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(KEY_SELECTION_TYPE, SelectorType::class.java)
        } else {
            arguments?.getSerializable(KEY_SELECTION_TYPE) as? SelectorType
        } ?: throw IllegalArgumentException("InValid TypeSelector")
    }
    private lateinit var sharedViewModel: TypeViewModel

    private val adapter by lazy {
        TypeSelectionAdapter(
            DummyTypeData.allTypes.map { it.toUiModel() },
            selectorType,
            sharedViewModel,
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedViewModel = ViewModelProvider(requireActivity(), TypeViewModel.factory()).get(TypeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTypeChoiceBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        binding.rvTypeChoice.adapter = adapter
        val decoration =
            GridSpacingItemDecoration(spanCount = 4, spacing = 20.dp, includeEdge = false)
        binding.rvTypeChoice.addItemDecoration(decoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "type_selection_bottom_sheet_fragment"
        private const val KEY_SELECTION_TYPE = "selection_type"

        fun newInstance(selectorType: SelectorType): TypeSelectionBottomSheetFragment {
            return TypeSelectionBottomSheetFragment().apply {
                arguments = Bundle().apply { putSerializable(KEY_SELECTION_TYPE, selectorType) }
            }
        }
    }
}
