package com.example.android.trackmysleepquality.sleeptracker

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.TextItemViewHolder
import com.example.android.trackmysleepquality.database.SleepNight

class SleepNightAdapter: RecyclerView.Adapter<TextItemViewHolder>() {
    var data = listOf<SleepNight>()
    set(value) { // Setter to replace data
        field = value
        notifyDataSetChanged() // RecyclerView redraws list with new data when this is called
    }

    /** Overridden functions are needed by RecyclerView **/

    /** Return item count to RecyclerView **/
    override fun getItemCount() = data.size

    /** Inflate and return view holder to RecyclerView **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context) // Used to inflate XML layouts
        val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView
        return TextItemViewHolder(view)
    }

    /** Return data for item at the specified position **/
    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.sleepQuality.toString()
        if(item.sleepQuality in listOf<Int>(0, 1)) { // Display red text for low sleep score
            holder.textView.setTextColor(Color.RED)
        }
    }
}
