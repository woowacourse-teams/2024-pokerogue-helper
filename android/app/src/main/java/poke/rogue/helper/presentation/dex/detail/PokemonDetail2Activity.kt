package poke.rogue.helper.presentation.dex.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout.LayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import poke.rogue.helper.R
import poke.rogue.helper.data.model.PokemonDetailSkills
import poke.rogue.helper.databinding.ActivityPokemonDetail3Binding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.dex.PokemonTypesAdapter
import poke.rogue.helper.presentation.dex.detail.PokemonDetailActivity.Companion
import poke.rogue.helper.presentation.dex.model.EvolutionsUiModel
import poke.rogue.helper.presentation.dex.model.PokemonUiModel
import poke.rogue.helper.presentation.dex.model.StatUiModel
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.type.view.TypeChip
import poke.rogue.helper.presentation.util.context.stringOf
import poke.rogue.helper.presentation.util.view.dp
import kotlin.math.abs

class PokemonDetail2Activity : BindingActivity<ActivityPokemonDetail3Binding>(R.layout.activity_pokemon_detail3) {

    private val sampleAdapter by lazy { SampleAdapter() }
    private lateinit var pokemonTypesAdapter: PokemonTypesAdapter

    private val getYMax by lazy {
        resources.getDimension(R.dimen.appbar_height) - resources.getDimension(
            R.dimen.toolbar_height
        )
    }

    private val fadeIn by lazy { AnimationUtils.loadAnimation(this, R.anim.fade_in) }

    private val fadeOut by lazy { AnimationUtils.loadAnimation(this, R.anim.fade_out) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.pokemonDetail = pokemonDetail
        pokemonTypesAdapter =
            PokemonTypesAdapter(
                context = this,
                viewGroup = binding.layoutPokemonDetailPokemonTypes,
            )
        pokemonTypesAdapter.addTypes(
            types = pokemonDetail.pokemon.types,
            config = PokemonDetailActivity.typesUiConfig,
            spacingBetweenTypes = 0.dp,
        )

        binding.tvPokemonDetailPokemonName.text =
            stringOf(
                R.string.pokemon_list_poke_name_format,
                pokemonDetail.pokemon.name,
                pokemonDetail.pokemon.dexNumber,
            )

        binding.pokemonType = TypeUiModel.FIRE
        binding.executePendingBindings()

        initTopBarAnimation()
        initRecyclerView()
        loadSample()
    }

    private fun initTopBarAnimation() {
        val appBarLayout = findViewById<AppBarLayout>(R.id.app_bar_layout)

        val back = findViewById<ImageView>(R.id.iv_back)
        val del = findViewById<ImageView>(R.id.iv_del)
        val share = findViewById<ImageView>(R.id.iv_share)

        appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            if (abs(verticalOffset) >= getYMax / 2) {
                if (back.visibility != View.GONE) {
                    back.startAnimation(fadeOut)
                    del.startAnimation(fadeOut)
                    share.startAnimation(fadeOut)

                    back.visibility = View.GONE
                    del.visibility = View.GONE
                    share.visibility = View.GONE
                }
            } else {
                if (back.visibility != View.VISIBLE) {
                    back.startAnimation(fadeIn)
                    del.startAnimation(fadeIn)
                    share.startAnimation(fadeIn)

                    back.visibility = View.VISIBLE
                    del.visibility = View.VISIBLE
                    share.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initRecyclerView() {
        with(findViewById<RecyclerView>(R.id.rv_main)) {
            adapter = sampleAdapter
        }
    }

    private fun loadSample() {
        sampleAdapter.notifySample()
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

class SampleAdapter : RecyclerView.Adapter<SampleAdapter.SampleViewHolder>() {

    private val items = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SampleViewHolder(parent)

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        //..
    }

    override fun getItemCount() = items.size

    fun notifySample() {
        items.clear()
        (1..10).forEach {
            items.add(it)
        }
        notifyDataSetChanged()
    }

    class SampleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_sample, parent, false),
    )
}

val pokemonDetail = PokemonDetailUiState.Success(
    pokemon = PokemonUiModel(
        id = "1",
        name = "리자몽",
        types = listOf(
            TypeUiModel.FIRE, TypeUiModel.FLYING,
        ),
        hashId = 0,
        dexNumber = 0,
        formName = "일반",
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
    ),
    stats = listOf(
        StatUiModel(
            name = "HP",
            amount = 45,
            limit = 100,
        ),
    ),
    abilities = listOf(),
    evolutions = EvolutionsUiModel(),
    skills = PokemonDetailSkills(
        emptyList(), emptyList(), emptyList(),
    ),
    height = 100F,
    weight = 100F,
    biomes = listOf(),
)