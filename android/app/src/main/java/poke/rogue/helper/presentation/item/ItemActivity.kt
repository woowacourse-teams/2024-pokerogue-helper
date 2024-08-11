package poke.rogue.helper.presentation.item

import android.content.Context
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityItemBinding
import poke.rogue.helper.presentation.toolbar.ToolbarActivity

class ItemActivity : ToolbarActivity<ActivityItemBinding>(R.layout.activity_item) {
    override val toolbar: Toolbar
        get() = binding.toolbarItem

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, ItemActivity::class.java)
        }
    }
}
