package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight

/** Constructor is set as private because from() function acts as constructor and returns ViewHolder object **/
class ViewHolder private constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
    companion object {
        /**
         * Inflate XML layout and return as ViewHolder object.
         *
         * Function is in a companion object so it can be called on from the ViewHolder class. This
         * function performs the role of a constructor and returns a ViewHolder object.
         */
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context) // Used to inflate XML layouts
            val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
            return ViewHolder(view)
        }
    }

    private val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
    private val quality: TextView = itemView.findViewById(R.id.quality_string)
    private val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)

    /**
     * Called by Adapter to bind data to inflated view at given position
     * @param item Position where data will be bound
     */
    fun bind(item: SleepNight) {
        val res = itemView.context.resources
        sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        quality.text = convertNumericQualityToString(item.sleepQuality, res)
        qualityImage.setImageResource(when (item.sleepQuality) {
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

class SleepNightAdapter: RecyclerView.Adapter<ViewHolder>() {
    var data = listOf<SleepNight>()
    set(value) { // Setter to replace data
        field = value
        notifyDataSetChanged() // RecyclerView redraws list with new data when this is called
    }

    /** Overridden functions are needed by RecyclerView **/

    /** Return item count to RecyclerView **/
    override fun getItemCount() = data.size

    /** Inflate and return view holder to RecyclerView **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    /** Return data for item at the specified position **/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }
}
