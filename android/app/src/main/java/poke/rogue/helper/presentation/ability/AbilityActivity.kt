package poke.rogue.helper.presentation.ability

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityAbilityBinding
import poke.rogue.helper.presentation.ability.detail.AbilityDetailFragment
import poke.rogue.helper.presentation.base.BindingActivity

class AbilityActivity : BindingActivity<ActivityAbilityBinding>(R.layout.activity_ability) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val abilityId = intent.getLongExtra(ABILITY_ID, INVALID_ABILITY)
            if (abilityId == INVALID_ABILITY) {
                navigateToAbilityList()
                return
            }

            navigateToAbilityDetail(abilityId)
        }
    }

    private fun navigateToAbilityList() {
        supportFragmentManager.commit {
            replace<AbilityFragment>(R.id.fragment_container_ability)
        }
    }

    private fun navigateToAbilityDetail(abilityId: Long) {
        supportFragmentManager.commit {
            replace<AbilityDetailFragment>(
                containerViewId = R.id.fragment_container_ability,
                args = AbilityDetailFragment.bundleOf(abilityId),
            )
        }
    }

    companion object {
        private const val ABILITY_ID = "abilityId"
        private const val INVALID_ABILITY = -1L

        fun intent(
            context: Context,
            abilityId: Long = INVALID_ABILITY,
        ): Intent =
            Intent(context, AbilityActivity::class.java).apply {
                putExtra(ABILITY_ID, abilityId)
            }
    }
}
