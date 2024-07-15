package poke.rogue.helper.presentation.type

import android.os.Bundle
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityTypeBinding
import poke.rogue.helper.local.dao.TypeDao
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.type.mapper.toResultUiModel
import poke.rogue.helper.presentation.type.model.TypeMatchedResultUiModel
import poke.rogue.helper.presentation.type.typeselection.TypeSelectionBottomSheetFragment

class TypeActivity : BindingActivity<ActivityTypeBinding>(R.layout.activity_type) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.ivTypeMyTypeContent.setOnClickListener {
            displayBottomSheet()
        }
        val dummy =
            TypeMatchedResultUiModel("페어리", R.drawable.img_property_tmp_2, true, "강한 타입", TypeDao.allTypes.map { it.toResultUiModel() })
        binding.typeResult = dummy
    }

    private fun displayBottomSheet() {
        TypeSelectionBottomSheetFragment().show(
            supportFragmentManager,
            TypeSelectionBottomSheetFragment.TAG,
        )
    }
}
