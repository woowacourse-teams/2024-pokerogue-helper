package poke.rogue.helper.presentation.dex.detail

import android.os.Bundle
import android.widget.LinearLayout.LayoutParams
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import poke.rogue.helper.R
import poke.rogue.helper.data.repository.DefaultDexRepository
import poke.rogue.helper.databinding.ActivityPokemonDetailBinding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.dex.PokemonTypesAdapter
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.dp
import poke.rogue.helper.presentation.util.view.loadImageWithProgress
import timber.log.Timber

class PokemonDetailActivity : BindingActivity<ActivityPokemonDetailBinding>(R.layout.activity_pokemon_detail) {
    private val viewModel by viewModels<PokemonDetailViewModel> {
        PokemonDetailViewModel.factory(DefaultDexRepository.instance())
    }

    private lateinit var pokemonTypesAdapter: PokemonTypesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updatePokemonDetail(intent.getLongExtra(POKEMON_ID, 2606))

        initViewPager()

        pokemonTypesAdapter =
            PokemonTypesAdapter(
                context = this,
                viewGroup = binding.layoutPokemonDetailPokemonTypes,
            )

        repeatOnStarted {
            viewModel.uiState.collect { pokemonDetail ->
                when (pokemonDetail) {
                    is PokemonDetailUiState.IsLoading -> return@collect
                    is PokemonDetailUiState.Success -> {
                        bindPokemonDetail(pokemonDetail)
                    }
                }
            }
        }
    }

    private fun initViewPager() {
        // ViewPager2 Adapter 셋팅
        val viewPager2Adatper = ViewPager2Adapter(this)
        viewPager2Adatper.addFragment(PokemonStatFragment())
        viewPager2Adatper.addFragment(PokemonEvolutionFragment())
        viewPager2Adatper.addFragment(PokemonMovesFragment())
        viewPager2Adatper.addFragment(PokemonInformationFragment())

        // Adapter 연결
        binding.pagerPokemonDetail.apply {
            adapter = viewPager2Adatper

            registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                },
            )
        }

        // ViewPager, TabLayout 연결
        TabLayoutMediator(binding.tabLayoutPokemonDetail, binding.pagerPokemonDetail) { tab, position ->
            Timber.d("ViewPager position: $position")
            when (position) {
                0 -> tab.text = "Stat"
                1 -> tab.text = "Evolve"
                2 -> tab.text = "Moves"
                3 -> tab.text = "Information"
            }
        }.attach()
    }

    private fun bindPokemonDetail(pokemonDetail: PokemonDetailUiState.Success) {
        with(binding) {
            ivPokemonDetailPokemon.loadImageWithProgress(pokemonDetail.pokemon.imageUrl, progressIndicatorPokemonDetail)

            tvPokemonDetailPokemonName.text =
                stringOf(
                    R.string.dex_poke_name_format,
                    pokemonDetail.pokemon.name,
                    pokemonDetail.pokemon.dexNumber,
                )
        }

        pokemonTypesAdapter.addTypes(
            types = pokemonDetail.pokemon.types,
            config = typesUiConfig,
            spacingBetweenTypes = 0.dp,
        )
    }

    companion object {
        private const val POKEMON_ID = "pokemonId"
        val TAG: String = PokemonDetailActivity::class.java.simpleName

        private val typesUiConfig =
            TypeChip.PokemonTypeViewConfiguration(
                width = LayoutParams.WRAP_CONTENT,
                nameSize = 16.dp,
                iconSize = 20.dp,
                hasBackGround = false,
            )
    }
}

class ViewPager2Adapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    var fragments: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)
        // TODO: notifyItemInserted!!
    }

    fun removeFragement() {
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
        // TODO: notifyItemRemoved!!
    }
}
