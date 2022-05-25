package com.bismark.onlineticket.Cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bismark.onlineticket.R
import com.bismark.onlineticket.data_layer.entities.CartWithTicket
import com.bismark.onlineticket.databinding.ShoppingCardItemBinding
import java.text.SimpleDateFormat
import java.util.*

class CartAdapter constructor(
    private val carts: List<CartWithTicket>,
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShoppingCardItemBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = carts[position]
        holder.binding(item)
    }

    override fun getItemCount(): Int {
        return carts.size
    }
}

class CartViewHolder(
    private val item: ShoppingCardItemBinding
) : RecyclerView.ViewHolder(item.root) {

    fun binding(cart: CartWithTicket) {
        val dateFormat = SimpleDateFormat("E, MMM dd - h:mm a", Locale.getDefault())
        item.imageView.setImageResource(cart.ticket.imageResource)
        item.descriptionTv.text = item.descriptionTv.context.getString(
            R.string.cart_description,
            cart.cart.noOfTicket.toString(),
            cart.ticket.price.toString()
        )
        item.nameTv.text = cart.ticket.itemName
        item.dateTv.text = dateFormat.format(cart.ticket.createdDate)
        item.checkbox.visibility = View.GONE
    }
}
