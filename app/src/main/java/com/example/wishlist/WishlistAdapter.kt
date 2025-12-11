package com.example.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wishlist.R

class WishlistAdapter(
    private val items: MutableList<Item>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val i = items[position]
        holder.nameTextView.text = i.name
        holder.priceTextView.text = "$" + String.format("%.2f", i.price)
        holder.urlTextView.text = i.url
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener,
        View.OnLongClickListener {

        var nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        var priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        var urlTextView: TextView = itemView.findViewById(R.id.urlTextView)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLongItemClick(position)
            }
            return true
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onLongItemClick(position: Int)
    }
}
