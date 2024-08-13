package poke.rogue.helper.presentation.battle

import androidx.appcompat.widget.Toolbar
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityBattleBinding
import poke.rogue.helper.presentation.toolbar.ToolbarActivity

class BattleActivity : ToolbarActivity<ActivityBattleBinding>(R.layout.activity_battle) {
    override val toolbar: Toolbar
        get() = binding.toolbarBattle
}
