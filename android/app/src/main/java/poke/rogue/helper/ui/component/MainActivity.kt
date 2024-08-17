package poke.rogue.helper.ui.component

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityMainBinding
import poke.rogue.helper.presentation.type.model.TypeUiModel
import poke.rogue.helper.presentation.util.repeatOnStarted
import poke.rogue.helper.presentation.util.view.dp

class MainActivity : AppCompatActivity() {
    val viewModel by viewModels<MyViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val pokeChipGroup = binding.pokeChipGroup
        repeatOnStarted {
            viewModel.types.collect {
                pokeChipGroup.submitList(it.map { selectableType ->
                    PokeChip.PokeChipSpec(
                        selectableType.id,
                        "",
                        leadingIconRes = selectableType.type.typeIconResId,
                        sizes = PokeChip.PokeChipSizes(
                            leadingIconSize = 28.dp
                        ),
                        colors = PokeChip.PokeChipColors(
                            selectedContainerColor = selectableType.type.typeColor,
                        ),
                        isSelected = selectableType.isSelected,
                        onSelect = viewModel::selectTypeChip
                    )
                })
            }
        }
    }
}

class MyViewModel : ViewModel() {
    private val _types =
        MutableStateFlow<List<SelectableType>>(TypeUiModel.entries.mapIndexed { index, typeUiModel ->
            SelectableType(
                index,
                typeUiModel,
                false
            )
        })
    val types: StateFlow<List<SelectableType>> = _types.asStateFlow()

    fun selectTypeChip(chipId: Int) {
        val newChips = _types.value.map { chip ->
            if (chip.id == chipId) {
                chip.copy(isSelected = !chip.isSelected)
            } else {
                chip
            }
        }
        _types.value = newChips
    }
}

data class SelectableType(
    val id: Int,
    val type: TypeUiModel,
    val isSelected: Boolean
)