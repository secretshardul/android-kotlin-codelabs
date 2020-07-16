package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

/** Constructor is set as private because from() function acts as constructor and returns ViewHolder object **/
class ViewHolder private constructor(private val binding: ListItemSleepNightBinding): RecyclerView.ViewHolder(binding.root) {
    companion object {
        /**
         * Inflate XML layout and return as ViewHolder object.
         *
         * Function is in a companion object so it can be called on from the ViewHolder class. This
         * function performs the role of a constructor and returns a ViewHolder object.
         */
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context) // Used to inflate XML layouts
            val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }

    /**
     * Called by Adapter to bind data to inflated view at given position
     * @param item Position where data will be bound
     */
    fun bind(item: SleepNight) {
        val res = itemView.context.resources
        binding.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        binding.qualityString.text = convertNumericQualityToString(item.sleepQuality, res)
        binding.qualityImage.setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
    }
}

/**
 * ListAdapter
 *
 * Helper class for reading, tracking and updating data. It takes `SleepNightDiffCallback()` as
 * callback function for diff checking.
 */
class SleepNightAdapter: ListAdapter<SleepNight, ViewHolder>(SleepNightDiffCallback()) {
    // No need of data variable. Item can be fetched using getItem()
    // ListAdapter keeps track of data and provides submitList() to update list

    /** Overridden functions are needed by ListAdapter **/

    /** Inflate and return view holder to RecyclerView **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    /** Return data for item at the specified position **/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) // getItem() provided by ListAdapter
        holder.bind(item)
    }
}


/** Helper class for diff check **/
class SleepNightDiffCallback: DiffUtil.ItemCallback<SleepNight>() {
    /** Return true if items are same **/
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    /** Return true if contents of items are same **/
    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }

}
