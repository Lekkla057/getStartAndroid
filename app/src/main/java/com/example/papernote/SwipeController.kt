package com.example.papernote


import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*

import android.renderscript.ScriptIntrinsicBLAS.LEFT
import android.renderscript.ScriptIntrinsicBLAS.RIGHT
import androidx.recyclerview.widget.ItemTouchHelper.Callback.*

import androidx.recyclerview.widget.RecyclerView
import javax.security.auth.callback.Callback

class SwipeController: Callback {
    fun getMovementFlags(
        recyclerView: RecyclerView?,
        viewHolder: RecyclerView.ViewHolder?
    ): Int {
        return makeMovementFlags(
            0,
            LEFT or RIGHT
        )
    }

    fun onMove(
        recyclerView: RecyclerView?,
        viewHolder: RecyclerView.ViewHolder?,
        target: RecyclerView.ViewHolder?
    ): Boolean {
        return false
    }

    fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {}
}