package poke.rogue.helper.presentation.home

import android.content.Intent
import android.os.Bundle
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityHomeBinding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.type.TypeActivity

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvTmp.setOnClickListener {
            Intent(this, TypeActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}
