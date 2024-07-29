package poke.rogue.helper.presentation.ability.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import poke.rogue.helper.R
import poke.rogue.helper.data.datasource.RemoteAbilityDataSource
import poke.rogue.helper.data.repository.DefaultAbilityRepository
import poke.rogue.helper.databinding.ActivityAbilityDetailBinding
import poke.rogue.helper.presentation.ability.model.toUi
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.util.context.drawableOf
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.context.toast
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.remote.ServiceModule
import timber.log.Timber

class AbilityDetailActivity :
    BindingActivity<ActivityAbilityDetailBinding>(R.layout.activity_ability_detail) {
    private val viewModel by viewModels<AbilityDetailViewModel> {
        AbilityDetailViewModel.factory(
            DefaultAbilityRepository(
                remoteAbilityDataSource =
                    RemoteAbilityDataSource(
                        abilityService =
                            ServiceModule.abilityService(),
                    ),
            ),
        )
    }

    private val adapter: AbilityDetailAdapter by lazy { AbilityDetailAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repeatOnStarted {
            val id = intent.getLongExtra(ID, -1)
            viewModel.updateAbilityDetail(id)
        }
        initViews()
        initAdapter()
        initObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_toolbar_pokerogue -> {
                navigateToPokeRogue()
            }

            R.id.item_toolbar_feedback -> {
                toast(R.string.toolbar_feedback)
            }

            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    private fun navigateToPokeRogue() {
        toast(R.string.toolbar_pokerogue)
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(stringOf(R.string.home_pokerogue_url)))
        startActivity(intent)
    }

    private fun initViews() =
        with(binding) {
            setSupportActionBar(toolbarAbilityDetail.toolbar)
            toolbarAbilityDetail.toolbar.overflowIcon = drawableOf(R.drawable.ic_menu)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

    private fun initAdapter() {
        val decoration = GridSpacingItemDecoration(3, 15.dp, false)
        binding.rvAbilityDetailPokemon.adapter = adapter
        binding.rvAbilityDetailPokemon.addItemDecoration(decoration)
    }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.abilityDetail.collect { abilityDetail ->
                when (abilityDetail) {
                    is AbilityDetailUiState.Loading -> {}

                    is AbilityDetailUiState.Success -> {
                        binding.abilityUiModel = abilityDetail.data.toUi()
                        adapter.submitList(abilityDetail.data.pokemons)
                    }
                }
            }
        }

        repeatOnStarted {
            viewModel.errorEvent.collect {
                toast(R.string.ability_detail_error)
            }
        }
        // todo navigate to pokemon detail
        repeatOnStarted {
            viewModel.navigationToPokemonDetailEvent.collect { pokemonId ->
                Timber.d("pokemonId: $pokemonId")
            }
        }
    }

    companion object {
        private const val ID = "id"

        fun intent(
            context: Context,
            id: Long,
        ): Intent {
            return Intent(context, AbilityDetailActivity::class.java).apply {
                putExtra(ID, id)
            }
        }
    }
}
