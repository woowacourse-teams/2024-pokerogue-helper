package poke.rogue.helper.presentation.biome

import android.content.Context
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBiomeBinding
import poke.rogue.helper.presentation.toolbar.ToolbarActivity

class BiomeActivity : ToolbarActivity<ActivityBiomeBinding>(R.layout.activity_biome) {

    override val toolbar: Toolbar
        get() = binding.toolbarBiome

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, BiomeActivity::class.java)
        }
    }
}
