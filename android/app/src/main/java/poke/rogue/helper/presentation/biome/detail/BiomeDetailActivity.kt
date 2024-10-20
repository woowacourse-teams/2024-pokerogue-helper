package poke.rogue.helper.presentation.biome.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayoutMediator
import poke.rogue.helper.R
import poke.rogue.helper.analytics.analyticsLogger
import poke.rogue.helper.data.repository.DefaultBiomeRepository
import poke.rogue.helper.databinding.ActivityBiomeDetailBinding
import poke.rogue.helper.presentation.base.error.ErrorHandleActivity
import poke.rogue.helper.presentation.base.error.ErrorHandleViewModel
import poke.rogue.helper.presentation.battle.BattleActivity
import poke.rogue.helper.presentation.dex.detail.PokemonDetailActivity
import poke.rogue.helper.presentation.util.context.startActivity
import poke.rogue.helper.presentation.util.logClickEvent
import poke.rogue.helper.presentation.util.repeatOnStarted

class BiomeDetailActivity : ErrorHandleActivity<ActivityBiomeDetailBinding>(R.layout.activity_biome_detail) {
    private lateinit var pagerAdapter: BiomeDetailPagerAdapter
    private val viewModel: BiomeDetailViewModel by viewModels {
        BiomeDetailViewModel.factory(
            DefaultBiomeRepository.instance(),
            analyticsLogger(),
        )
    }
    override val errorViewModel: ErrorHandleViewModel
        get() = viewModel
    override val toolbar: Toolbar
        get() = binding.toolbarBiomeDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val biomeId = intent.getStringExtra(BIOME_ID).orEmpty()
            viewModel.init(biomeId)
        }
        binding.vm = viewModel
        binding.lifecycleOwner = this
        initAdapter()
        initObservers()
    }

    private fun initAdapter() {
        pagerAdapter = BiomeDetailPagerAdapter(this)
        binding.vpBiome.apply {
            adapter = pagerAdapter
        }

        val tabTitles = resources.getStringArray(R.array.biome_tab_titles)
        TabLayoutMediator(binding.tablayoutBiomeDetail, binding.vpBiome) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is BiomeDetailUiEvent.NavigateToNextBiomeDetail -> {
                        val biomeId = event.biomeId
                        startActivity<BiomeDetailActivity> {
                            putExtras(intent(this@BiomeDetailActivity, biomeId))
                            analyticsLogger().logClickEvent(NAVIGATE_TO_NEXT_BIOME_DETAIL)
                        }
                    }
                    is BiomeDetailUiEvent.NavigateToPokemonDetail -> {
                        val pokemonId = event.pokemonId
                        startActivity<PokemonDetailActivity> {
                            putExtras(PokemonDetailActivity.intent(this@BiomeDetailActivity, pokemonId))
                        }
                    }

                    is BiomeDetailUiEvent.NavigateToBattle -> {
                        val pokemonId = event.pokemonId
                        startActivity<BattleActivity> {
                            putExtras(BattleActivity.intent(this@BiomeDetailActivity, pokemonId, isMine = false))
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val BIOME_ID = "biomeId"
        private const val NAVIGATE_TO_NEXT_BIOME_DETAIL = "Nav_Next_Biome_Detail"

        fun intent(
            context: Context,
            biomeId: String,
        ): Intent =
            Intent(context, BiomeDetailActivity::class.java)
                .putExtra(BIOME_ID, biomeId)
    }
}
