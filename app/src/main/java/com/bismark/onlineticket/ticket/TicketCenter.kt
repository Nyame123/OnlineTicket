package com.bismark.onlineticket.ticket

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bismark.onlineticket.R
import com.bismark.onlineticket.app.OnlineTicketApp
import com.bismark.onlineticket.data_layer.entities.Ticket
import com.bismark.onlineticket.databinding.FragmentTicketCenterBinding
import com.bismark.onlineticket.di.RoomTicketProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class TicketCenter : Fragment() {

    private var ticketList = mutableListOf<Ticket>()
    private val ticketAdapter: TicketAdapter by lazy {
        TicketAdapter(ticketList, viewModel = ticketViewModel)
    }

    private var _binding: FragmentTicketCenterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var ticketProvider: RoomTicketProvider
    private val ticketViewModel: TicketViewModel by lazy {
        ViewModelProvider(this, TicketViewModelFactory(ticketProvider)).get(TicketViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OnlineTicketApp.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTicketCenterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ticketViewModel.fetchAllTicketItems()
        setUpRecyclerView()
        ticketViewModel.ticketLiveData.observe(viewLifecycleOwner) { tickets ->
            ticketList.clear()
            ticketList.addAll(tickets)
            ticketAdapter.notifyDataSetChanged()
        }

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    fun setUpRecyclerView() {
        with(binding.recyclerView) {
            adapter = ticketAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
