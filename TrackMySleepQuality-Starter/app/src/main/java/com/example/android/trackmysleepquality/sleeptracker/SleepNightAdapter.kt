package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): HeaderViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.header, parent, false)
            return HeaderViewHolder(view)
        }
    }
}

/** Constructor is set as private because from() function acts as constructor and returns ViewHolder object **/
class SleepNightViewHolder private constructor(private val binding: ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root) {
    companion object {
        /**
         * Inflate XML layout and return as ViewHolder object.
         *
         * Function is in a companion object so it can be called on from the ViewHolder class. This
         * function performs the role of a constructor and returns a ViewHolder object.
         */
        fun from(parent: ViewGroup): SleepNightViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context) // Used to inflate XML layouts
            val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
            return SleepNightViewHolder(binding)
        }
    }

    /**
     * Called by Adapter to bind data to inflated view at given position
     * @param item Position where data will be bound
     */
    fun bind(item: SleepNight, clickListener: SleepNightClickListener) {
        binding.sleepNight = item
        binding.clickListener = clickListener
        binding.executePendingBindings() // Optimization for RecyclerView
    }
}

/**
 * ListAdapter
 *
 * Helper class for reading, tracking and updating data. It takes `SleepNightDiffCallback()` as
 * callback function for diff checking.
 */
class SleepNightAdapter(private val clickListener: SleepNightClickListener): ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()) {
    // No need of data variable. Item can be fetched using getItem()
    // ListAdapter keeps track of data and provides submitList() to update list

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    /** Add header at 0th position. Use instead of submitList() in SleepTrackerFragment **/
    fun addHeaderAndSubmitList(list: List<SleepNight>?) {
        // Use coroutine for async operation
        adapterScope.launch {
            val items = when(list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
            // Main thread used because UI has to be updated
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    /** Differentiate header from sleep data and assign `viewType` parameter. **/
    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            else -> ITEM_VIEW_TYPE_ITEM
        }
    }

    /** Detect data type, inflate accordingly and return view holder to RecyclerView **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            else -> SleepNightViewHolder.from(parent)
        }
    }

    /** Return data for item at the specified position **/
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is SleepNightViewHolder -> {
                val nightItem = getItem(position) as DataItem.SleepNightItem // getItem() provided by ListAdapter
                holder.bind(nightItem.sleepNight, clickListener)
            }
            // No binding involved for header
        }

    }
}


/** Helper class for diff check **/
class SleepNightDiffCallback: DiffUtil.ItemCallback<DataItem>() {
    /** Return true if items are same **/
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    /** Return true if contents of items are same **/
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

// Constructor takes clickListener() function as input
class SleepNightClickListener(val clickListener: (nightId: Long) -> Unit) {
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}

/** Sealed class: All subclasses must be declared within its body. This prevents bugs. **/
sealed class DataItem {
    abstract val id: Long

    data class SleepNightItem(val sleepNight: SleepNight): DataItem() {
        override val id = sleepNight.nightId
    }

    // Header as object because only one instance is needed.
    object Header: DataItem() {
        override val id = Long.MIN_VALUE // So header ID never conflicts with sleep item ID
    }
}
