package poke.rogue.helper.presentation.util.view

import androidx.recyclerview.widget.DiffUtil

/**
 * [DiffUtil.ItemCallback] 는 두 리스트의 아이템을 비교하여 변경사항을 계산합니다.
 *
 * @param T 아이템 타입
 * @param onItemsTheSame 아이템이 같은지 여부를 판단하는 함수
 * @param onContentsTheSame 아이템의 내용이 같은지 여부를 판단하는 함수
 *
 * ```kotlin
 * data class Item(val id: Int, val name: String)
 *
 * class MyRecyclerViewAdapter : ListAdapter<Item, MyViewHolder>(itemComparator) {
 *     companion object {
 *         val itemComparator = ItemDiffCallback<Item>(
 *             onItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
 *             onContentsTheSame = { oldItem, newItem -> oldItem == newItem }
 *         )
 *     }
 * }
 * ```
 */
class ItemDiffCallback<T : Any>(
    val onItemsTheSame: (T, T) -> Boolean,
    val onContentsTheSame: (T, T) -> Boolean,
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(
        oldItem: T,
        newItem: T,
    ): Boolean = onItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(
        oldItem: T,
        newItem: T,
    ): Boolean = onContentsTheSame(oldItem, newItem)
}
