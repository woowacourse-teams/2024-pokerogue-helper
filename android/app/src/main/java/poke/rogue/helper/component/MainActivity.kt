package poke.rogue.helper.component

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val pokeChipGroup = findViewById<PokeChipGroup>(R.id.poke_chip_group)
        val chips = List(10) {
            if (it % 4 == 0) {
                PokeChip.PokeChipSpec(
                    id = it,
                    label = "Dark $it",
                    leadingIconRes = R.drawable.icon_type_dark,
                    isSelected = false
                )
            } else if (it % 3 == 0) {
                PokeChip.PokeChipSpec(
                    id = it,
                    label = "Fairy $it",
                    leadingIconRes = R.drawable.icon_type_fairy,
                    isSelected = true
                )
            } else {
                PokeChip.PokeChipSpec(
                    id = it,
                    label = "Bug $it",
                    leadingIconRes = R.drawable.icon_type_bug,
                    isSelected = false
                )
            }
        }
        pokeChipGroup.submitList(chips)
        lifecycleScope.launch {
            delay(3000)
            val newChips = chips.map { chip ->
                chip.copy(isSelected = !chip.isSelected)
            }
            pokeChipGroup.submitList(newChips)
        }
    }
}
