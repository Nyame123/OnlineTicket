package com.bismark.onlineticket.Cart

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bismark.onlineticket.R
import com.bismark.onlineticket.Utils
import com.bismark.onlineticket.app.OnlineTicketApp
import com.bismark.onlineticket.data_layer.entities.CartWithTicket
import com.bismark.onlineticket.databinding.FragmentCartScreenBinding
import com.bismark.onlineticket.di.RoomTicketProvider
import javax.inject.Inject

class CartScreen : Fragment() {

    private var _binding: FragmentCartScreenBinding? = null
    private val binding get() = _binding!!

    private var cartList = mutableListOf<CartWithTicket>()
    private val cartAdapter: CartAdapter by lazy {
        CartAdapter(cartList)
    }

    @Inject
    lateinit var cartProvider: RoomTicketProvider
    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(this, CartViewModelFactory(cartProvider)).get(CartViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OnlineTicketApp.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCartScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        drawBackgroundAroundSubtotal()

        cartViewModel.fetchAllCartItems()

        cartViewModel.cartLiveData.observe(viewLifecycleOwner) {
            cartList.addAll(it)
            cartAdapter.notifyDataSetChanged()
        }
        cartViewModel.subtotalCostLivaData.observe(viewLifecycleOwner) {
            binding.subTotalTv.text = context?.getString(R.string.subtotal_cost, it.toString())
        }
        cartViewModel.noCartItemiveData.observe(viewLifecycleOwner){
            findNavController().popBackStack()
            Toast.makeText(requireContext(),"No Item added to cart yet",Toast.LENGTH_SHORT).show()
        }
    }

    fun drawBackgroundAroundSubtotal() {
        val rectangularShape = GradientDrawable()
        rectangularShape.setStroke(
            Utils.convertIntToDp(requireContext(), 2),
            requireContext().resources.getColor(R.color.black)
        )
        rectangularShape.shape = GradientDrawable.RECTANGLE
        rectangularShape.color =
            ColorStateList.valueOf(requireContext().resources.getColor(R.color.white))

        binding.subTotalTv.background = rectangularShape
    }

    fun setUpRecyclerView() {
        binding.recyclerView.adapter = cartAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
