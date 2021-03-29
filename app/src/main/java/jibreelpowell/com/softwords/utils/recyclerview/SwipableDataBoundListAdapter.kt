package jibreelpowell.com.softwords.utils.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil

abstract class SwipableDataBoundListAdapter<T, V : ViewDataBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : DataBoundListAdapter<T, V>(diffCallback) {

    val removedItems = arrayListOf<T>()

    fun removeItem(position: Int): T? {
        if (position < 0 || position >= itemCount) return null
        TODO()
    }
}