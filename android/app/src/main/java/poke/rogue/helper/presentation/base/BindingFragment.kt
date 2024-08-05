package poke.rogue.helper.presentation.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import poke.rogue.helper.R
import poke.rogue.helper.presentation.util.fragment.drawableOf

abstract class BindingFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int,
) : Fragment() {
    private var _binding: T? = null
    protected val binding get() = _binding ?: error("Binding is not initialized")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding?.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }

    abstract fun toolbar(): Toolbar?

    private fun initToolbar() {
        toolbar()?.apply {
            inflateMenu(R.menu.menu_toolbar)
            overflowIcon = drawableOf(R.drawable.ic_menu)
            setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.item_toolbar_pokerogue -> {
                        navigateToPokeRogue()
                    }

                    R.id.item_toolbar_feedback -> {}
                }
                true
            }
        }
    }

    private fun navigateToPokeRogue() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.home_pokerogue_url)))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
