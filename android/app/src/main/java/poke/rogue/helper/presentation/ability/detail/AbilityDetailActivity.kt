package poke.rogue.helper.presentation.ability.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityAbilityDetailBinding
import poke.rogue.helper.presentation.ability.AbilityUiModel
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.poketmon2.PokemonUiModel
import poke.rogue.helper.presentation.util.context.drawableOf
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.context.toast
import poke.rogue.helper.presentation.util.view.GridSpacingItemDecoration
import poke.rogue.helper.presentation.util.view.dp

// todo abilityDetailUiModel, 포켓몬 클릭시 도감상세 페이지로 이동?
class AbilityDetailActivity :
    BindingActivity<ActivityAbilityDetailBinding>(R.layout.activity_ability_detail) {
    private val adapter: AbilityDetailAdapter by lazy { AbilityDetailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.abilityUiModel = AbilityUiModel.DUMMY
        initViews()
        initAdapter()
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
        initDummy()
        val decoration = GridSpacingItemDecoration(3, 15.dp, false)
        binding.rvAbilityDetailPokemon.adapter = adapter
        binding.rvAbilityDetailPokemon.addItemDecoration(decoration)
    }

    private fun initDummy() {
        adapter.submitList(
            PokemonUiModel.dummys(10),
        )
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, AbilityDetailActivity::class.java)
        }
    }
}
