package com.example.papernote.main


import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.papernote.R
import com.example.papernote.convertString
import com.example.papernote.database.Record


class MainAdapter(val adapterOnClick: (Record) -> Unit, val update: (Record?, Any?) -> Unit) :
    ListAdapter<Record, MainAdapter.ViewHolder>(SleepNightDiffCallback()) {
    var status = false

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val res = holder.itemView.context.resources

        holder.title.text = convertString(item.title, res)

//        holder.imgEdit.setOnClickListener {
//            dialog(it, item)
//
//        }
        if (status == true) {
            holder.imgEdit.visibility = View.VISIBLE
            holder.nextIcon.visibility = View.GONE
            holder.next.setOnClickListener {
                dialog(it, item)
            }

        } else {
            holder.imgEdit.visibility = View.GONE
            holder.nextIcon.visibility = View.VISIBLE
            holder.next.setOnClickListener {

                it.findNavController()
                    .navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(item.noteId))
            }
        }
    }

    fun dialog(view: View, item: Record) {

        val inflater = LayoutInflater.from(view.context)
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Edit Title Name")
        val dialogView: View =
            inflater.inflate(R.layout.fragment_popup, null)
        val editText = dialogView.findViewById<EditText>(R.id.editText2)
        editText.setText(item.title)
        builder.setPositiveButton("OK") { dialogInterface, i ->
            update(item,editText.text.toString())
            dialogInterface.dismiss();


        }
        builder.setView(dialogView)
        val dialog: AlertDialog = builder.create()

        dialog.show()

    }

    fun removeAt(i: Int) {
        val item = getItem(i)
        adapterOnClick(item)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {

        val layoutInflater =
            LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(
                R.layout.list_title,
                parent, false
            )

        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val next: ConstraintLayout = itemView.findViewById(R.id.next)
        val imgEdit: ImageView = itemView.findViewById(R.id.imgEdit)
        val nextIcon: ImageView = itemView.findViewById(R.id.imageView)
    }

}

class SleepNightDiffCallback : DiffUtil.ItemCallback<Record>() {
    override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem.noteId == newItem.noteId
    }

    override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem == newItem
    }
}
