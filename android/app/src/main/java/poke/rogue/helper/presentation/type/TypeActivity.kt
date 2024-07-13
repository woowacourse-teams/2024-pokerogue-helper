package poke.rogue.helper.presentation.type

import android.os.Bundle
import poke.rogue.helper.R
import poke.rogue.helper.data.mapper.toResultUiModel
import poke.rogue.helper.databinding.ActivityTypeBinding
import poke.rogue.helper.local.dao.TypeDao
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.type.model.TypeMatchedResultUiModel
import poke.rogue.helper.presentation.type.type_selection.TypeSelectionBottomSheetFragment

class TypeActivity : BindingActivity<ActivityTypeBinding>(R.layout.activity_type) {
    private val bottomSheet by lazy {
        TypeSelectionBottomSheetFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.ivTypeMyTypeContent.setOnClickListener {
            displayBottomSheet()
        }

        val dummy =
            TypeMatchedResultUiModel("페어리", R.drawable.img_property_tmp_2, true, "강한 타입", TypeDao.allTypes.map { it.toResultUiModel() })
        binding.includedTypeStrong.bind(dummy)
    }

    private fun displayBottomSheet() {
        bottomSheet.show(supportFragmentManager, TypeSelectionBottomSheetFragment.TAG)
    }
}
