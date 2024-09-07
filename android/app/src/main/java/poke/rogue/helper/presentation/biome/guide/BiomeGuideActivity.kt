package poke.rogue.helper.presentation.biome.guide

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBiomeGuideBinding
import poke.rogue.helper.presentation.base.toolbar.ToolbarActivity
import poke.rogue.helper.presentation.util.context.stringOf

class BiomeGuideActivity :
    ToolbarActivity<ActivityBiomeGuideBinding>(R.layout.activity_biome_guide) {
    override val toolbar: Toolbar
        get() = binding.toolbarBiomeGuide

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding.wvBiomeGuide) {
            settings.loadWithOverviewMode = true
            loadUrl(stringOf(R.string.biome_guide_url))
        }
    }
}
