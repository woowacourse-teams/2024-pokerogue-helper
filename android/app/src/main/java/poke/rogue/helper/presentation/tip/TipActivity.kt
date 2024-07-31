package poke.rogue.helper.presentation.tip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebViewClient
import poke.rogue.helper.R
import poke.rogue.helper.databinding.ActivityTipBinding
import poke.rogue.helper.presentation.base.BindingActivity
import poke.rogue.helper.presentation.util.context.stringOf

class TipActivity : BindingActivity<ActivityTipBinding>(R.layout.activity_tip) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding.webView) {
            settings.loadWithOverviewMode = true
            webViewClient = WebViewClient()
            loadUrl(stringOf(R.string.home_pokerogue_tip_url))
        }
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, TipActivity::class.java)
        }
    }
}
