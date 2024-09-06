package poke.rogue.helper.presentation.biome

import android.os.Bundle
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBiomeGuideBinding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.util.context.stringOf

class BiomeGuideActivity :
    BindingActivity<ActivityBiomeGuideBinding>(R.layout.activity_biome_guide) {
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
