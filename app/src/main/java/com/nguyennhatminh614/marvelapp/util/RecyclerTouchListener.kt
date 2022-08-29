package com.nguyennhatminh614.marvelapp.util

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerTouchListener: RecyclerView.OnItemTouchListener {
    private var context: Context? = null
    private var clickListener: ClickListener? = null
    private val gestureDetector: GestureDetector =
        GestureDetector(
            context,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }
            }
        )

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener?.onClick(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        /* TODO not implement */
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        /* TODO not implement */
    }

    fun setContext(context: Context){
        this.context = context
    }

    fun registerClickNavigationDrawerItem(clickListener: ClickListener){
        this.clickListener = clickListener
    }
}

interface ClickListener {
    fun onClick(view: View, position: Int)
}
