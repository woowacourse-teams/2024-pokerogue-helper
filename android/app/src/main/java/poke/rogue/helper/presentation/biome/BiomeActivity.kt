package poke.rogue.helper.presentation.biome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.analytics.AnalyticsLogger
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.databinding.ActivityBiomeBinding
import poke.rogue.helper.presentation.biome.detail.BiomeDetailActivity
import poke.rogue.helper.presentation.biome.model.BiomeUiModel
import poke.rogue.helper.presentation.toolbar.ToolbarActivity
import poke.rogue.helper.presentation.util.context.startActivity
import poke.rogue.helper.presentation.util.logClickEvent
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import timber.log.Timber

class BiomeActivity : ToolbarActivity<ActivityBiomeBinding>(R.layout.activity_biome) {

    private val logger: AnalyticsLogger = analyticsLogger()
    private val viewModel by viewModels<BiomeViewModel>()
    private val adapter: BiomeAdapter by lazy { BiomeAdapter(viewModel) }
    override val toolbar: Toolbar
        get() = binding.toolbarBiome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        val decoration = GridSpacingItemDecoration(2, 9.dp, false)
        binding.rvBiomeList.adapter = adapter
        BiomeUiModel.DUMMYS.forEach {
            adapter.submitList(BiomeUiModel.DUMMYS)
        }

        binding.rvBiomeList.addItemDecoration(decoration)
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.navigationToDetailEvent.collect { biomeId ->
                startActivity<BiomeDetailActivity> {
                    putExtra(BiomeDetailActivity.BIOME_ID, biomeId)
                    logger.logClickEvent(NAVIGATE_TO_BIOME_DETAIL)
                }
            }
        }
    }

    companion object {
        private const val NAVIGATE_TO_BIOME_DETAIL = "Nav_Biome_Detail"
    }
}
