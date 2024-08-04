package poke.rogue.helper.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityHomeBinding
import poke.rogue.helper.presentation.ability.AbilityActivity
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.dex.PokemonActivity
import poke.rogue.helper.presentation.tip.TipActivity
import poke.rogue.helper.presentation.type.TypeActivity
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.repeatOnStarted

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initObservers()
    }

    override fun toolBar(): Toolbar = binding.toolbarHome

    private fun initViews() =
        with(binding) {
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            actionHandler = viewModel
        }

    private fun initObservers() {
        repeatOnStarted {
            viewModel.navigationEvent.collect { state ->
                when (state) {
                    is HomeNavigateEvent.ToType ->
                        TypeActivity.intent(this)
                            .also { startActivity(it) }

                    is HomeNavigateEvent.ToDex ->
                        PokemonActivity.intent(this)
                            .also { startActivity(it) }

                    is HomeNavigateEvent.ToAbility ->
                        AbilityActivity.intent(this)
                            .also { startActivity(it) }

                    is HomeNavigateEvent.ToTip ->
                        TipActivity.intent(this)
                            .also { startActivity(it) }

                    is HomeNavigateEvent.ToLogo -> navigateToPokeRogue()
                }
            }
        }
    }

    private fun navigateToPokeRogue() {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse(stringOf(R.string.home_pokerogue_url)),
        ).also { startActivity(it) }
    }
}
