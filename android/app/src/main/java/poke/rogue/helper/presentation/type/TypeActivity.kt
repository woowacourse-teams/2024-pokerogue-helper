package poke.rogue.helper.presentation.type

import android.os.Bundle
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityTypeBinding
import poke.rogue.helper.presentation.base.BindingActivity
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
    }

    private fun displayBottomSheet() {
        bottomSheet.show(supportFragmentManager, TypeSelectionBottomSheetFragment.TAG)
    }
}
