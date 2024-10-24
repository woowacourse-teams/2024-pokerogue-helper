package poke.rogue.helper.presentation.biome

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultBiomeRepository
import poke.rogue.helper.databinding.ActivityBiomeBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleActivity
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.biome.detail.BiomeDetailActivity
import poke.rogue.helper.presentation.biome.guide.BiomeGuideActivity
import poke.rogue.helper.presentation.biome.model.toUi
import poke.rogue.helper.presentation.util.activity.hideKeyboard
import poke.rogue.helper.presentation.util.context.startActivity
import poke.rogue.helper.presentation.util.logClickEvent
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

class BiomeActivity : ErrorHandleActivity<ActivityBiomeBinding>(R.layout.activity_biome) {
    private val viewModel by viewModels<BiomeViewModel> {
        BiomeViewModel.factory(
            DefaultBiomeRepository.instance(applicationContext),
        )
    }
    override val errorViewModel: ErrorHandleViewModel
        get() = viewModel
    private val biomeAdapter: BiomeAdapter by lazy { BiomeAdapter(viewModel) }
    override val toolbar: Toolbar
        get() = binding.toolbarBiome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
        initView()
        initAdapter()
        initObservers()
    }

    private fun initView() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun initListener() {
        binding.rvBiomeList.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
    }

    private fun initAdapter() {
        binding.rvBiomeList.apply {
            val spanCount =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2
            adapter = biomeAdapter
            layoutManager = GridLayoutManager(context, spanCount)
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount,
                    9.dp,
                    false,
                ),
            )
        }
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.navigationToDetailEvent.collect { biomeId ->
                startActivity<BiomeDetailActivity> {
                    putExtras(BiomeDetailActivity.intent(this@BiomeActivity, biomeId))
                    logger.logClickEvent(NAVIGATE_TO_BIOME_DETAIL)
                }
            }
        }

        repeatOnStarted {
            viewModel.navigateToGuideEvent.collect {
                startActivity<BiomeGuideActivity> {
                    logger.logClickEvent(NAVIGATE_TO_BIOME_GUIDE)
                }
            }
        }

        repeatOnStarted {
            viewModel.biomes.collect { biome ->
                when (biome) {
                    is BiomeUiState.Loading -> {
                        binding.biomeLoading.isVisible = true
                    }

                    is BiomeUiState.Success -> {
                        biomeAdapter.submitList(biome.data.toUi()) {
                            binding.rvBiomeList.scrollToPosition(0)
                        }
                        binding.biomeLoading.isVisible = false
                    }
                }
            }
        }
    }

    companion object {
        private const val NAVIGATE_TO_BIOME_DETAIL = "Nav_Biome_Detail"
        private const val NAVIGATE_TO_BIOME_GUIDE = "Nav_Biome_Guide"
    }
}
