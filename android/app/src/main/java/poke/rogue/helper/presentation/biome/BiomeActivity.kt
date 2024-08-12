package poke.rogue.helper.presentation.biome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBiomeBinding
import poke.rogue.helper.presentation.biome.model.BiomeUiModel
import poke.rogue.helper.presentation.toolbar.ToolbarActivity
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class BiomeActivity : ToolbarActivity<ActivityBiomeBinding>(R.layout.activity_biome) {

    private val adapter: BiomeAdapter by lazy { BiomeAdapter() }
    override val toolbar: Toolbar
        get() = binding.toolbarBiome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        val decoration = GridSpacingItemDecoration(2, 9.dp, false)
        binding.rvBiomeList.adapter = adapter
        BiomeUiModel.DUMMYS.forEach {
            adapter.submitList(BiomeUiModel.DUMMYS)
        }

        binding.rvBiomeList.addItemDecoration(decoration)
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, BiomeActivity::class.java)
        }
    }
}
