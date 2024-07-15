package poke.rogue.helper.presentation.type.typeselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import poke.rogue.helper.databinding.FragmentTypeChoiceBottomSheetBinding
import poke.rogue.helper.local.dao.TypeDao
import poke.rogue.helper.presentation.type.mapper.toSelectionUiModel
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class TypeSelectionBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTypeChoiceBottomSheetBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val adapter by lazy {
        TypeSelectionAdapter(TypeDao.allTypes.map { it.toSelectionUiModel() })
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
            GridSpacingItemDecoration(spanCount = 4, spacing = 19.dp, includeEdge = false)
        binding.rvTypeChoice.addItemDecoration(decoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "type_selection_bottom_sheet_fragment"
    }
}
