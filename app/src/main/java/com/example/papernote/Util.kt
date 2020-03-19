package com.example.papernote

import android.content.res.Resources
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
fun convertString(title: String, res: Resources): String {
    // val durationMilli = endTimeMilli - startTimeMilli
    // val weekdayString = SimpleDateFormat("EEEE", Locale.getDefault()).format(startTimeMilli)
    return title.toString()
}
class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)