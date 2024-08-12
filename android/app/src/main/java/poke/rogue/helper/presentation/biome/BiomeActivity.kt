package poke.rogue.helper.presentation.biome

import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBiomeBinding
import poke.rogue.helper.presentation.base.toolbar.ToolbarActivity

class BiomeActivity : ToolbarActivity<ActivityBiomeBinding>(R.layout.activity_biome) {
    override val toolbar: Toolbar
        get() = binding.toolbarBiome
}
