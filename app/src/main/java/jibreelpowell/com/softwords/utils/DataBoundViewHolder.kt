package jibreelpowell.com.softwords.utils

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * A generic ViewHolder that works with a [ViewDataBinding].
 * @param <T> The type of the ViewDataBinding.
 * @see <a href="https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/ui/common/DataBoundViewHolder.kt">Original implmentation</a>
 */
class DataBoundViewHolder<out T: ViewDataBinding> constructor(val binding: T) :
    RecyclerView.ViewHolder(binding.root)