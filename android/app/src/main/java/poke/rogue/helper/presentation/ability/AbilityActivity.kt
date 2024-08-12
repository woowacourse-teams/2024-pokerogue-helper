package poke.rogue.helper.presentation.ability

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityAbilityBinding
import poke.rogue.helper.presentation.base.BindingActivity

class AbilityActivity : BindingActivity<ActivityAbilityBinding>(R.layout.activity_ability) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace<AbilityFragment>(R.id.fragment_container_ability)
            }
        }
    }

    companion object {
        private const val ABILITY_ID = "ABILITY_ID"

        fun intent(context: Context): Intent {
            return Intent(context, AbilityActivity::class.java)
        }

        fun intent(
            context: Context,
            abilityId: Long,
        ): Intent =
            Intent(context, AbilityActivity::class.java).apply {
                putExtra(ABILITY_ID, abilityId)
            }
    }
}
