package com.bismark.onlineticket.ticket

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bismark.onlineticket.Utils
import com.bismark.onlineticket.data_layer.entities.Ticket
import com.bismark.onlineticket.databinding.ShoppingCardItemBinding
import java.text.SimpleDateFormat
import java.util.*

class TicketAdapter constructor(
    private val tickets: List<Ticket>,
    private val viewModel: TicketViewModel
) : RecyclerView.Adapter<TicketViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = ShoppingCardItemBinding.inflate(inflator, parent, false)
        return TicketViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bindView(tickets[position])
    }

    override fun getItemCount(): Int {
        return tickets.size
    }
}

class TicketViewHolder constructor(
    val item: ShoppingCardItemBinding,
    val viewModel: TicketViewModel
) :
    RecyclerView.ViewHolder(item.root) {

    fun bindView(ticket: Ticket) {
        val dateFormat = SimpleDateFormat("E, MMM dd - h:mm a", Locale.getDefault())
        item.imageView.setImageResource(ticket.imageResource)
        item.descriptionTv.text = ticket.description
        item.nameTv.text = ticket.itemName
        item.dateTv.text = dateFormat.format(ticket.createdDate)

        item.checkbox.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                createDialog(compoundButton.context, ticket, item.checkbox)
            }
        }
    }

    fun createDialog(context: Context, ticket: Ticket, checkBox: CheckBox) {
        val ticketNumberEdittext = EditText(context)
        ticketNumberEdittext.hint = "Enter the number of tickets to buy here"
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        ticketNumberEdittext.setPadding(
            Utils.convertIntToDp(context, 16),
            Utils.convertIntToDp(context, 16),
            Utils.convertIntToDp(context, 16),
            Utils.convertIntToDp(context, 16)
        )
        ticketNumberEdittext.layoutParams = layoutParams

        Utils.createDialog(
            context,
            ticketNumberEdittext,
            "Add Ticket to Cart", {
                if (ticketNumberEdittext.text.isNotEmpty()) {
                    viewModel.addToCart(
                        ticket,
                        ticketNumberEdittext.text.toString().toInt()
                    )
                    checkBox.isChecked = true
                }else{
                    checkBox.isChecked = false
                    Toast.makeText(context,"Enter number of ticket required",Toast.LENGTH_SHORT).show()
                }
                it.cancel()
                it.dismiss()
            }, {
                checkBox.isChecked = false
                it.cancel()
                it.dismiss()
            })
    }
}
