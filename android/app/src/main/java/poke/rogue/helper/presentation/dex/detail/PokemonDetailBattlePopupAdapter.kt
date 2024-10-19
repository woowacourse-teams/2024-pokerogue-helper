package poke.rogue.helper.presentation.dex.detail

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class PokemonDetailBattlePopupAdapter(private val items: List<BattlePopUpUiModel> = BattlePopUpUiModel.items) :
    BaseAdapter() {
    override fun getCount(): Int = items.size
    override fun getItem(position: Int): Any = items[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (convertView != null) {
            return convertView
        }

        val holder = BattlePopUpViewHolder.inflated(parent)
        return holder.bind(items[position])
    }
}
