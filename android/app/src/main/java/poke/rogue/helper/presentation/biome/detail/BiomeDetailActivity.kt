package poke.rogue.helper.presentation.biome.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBiomeDetailBinding
import poke.rogue.helper.presentation.base.toolbar.ToolbarActivity

class BiomeDetailActivity :
    ToolbarActivity<ActivityBiomeDetailBinding>(R.layout.activity_biome_detail) {
    override val toolbar: Toolbar
        get() = binding.toolbarBiomeDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        const val BIOME_ID = "biomeId"

        fun intent(
            context: Context,
            biomeId: String,
        ): Intent =
            Intent(context, BiomeDetailActivity::class.java)
                .putExtra(BIOME_ID, biomeId)
    }
}
