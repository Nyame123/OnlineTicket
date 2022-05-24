package com.bismark.onlineticket.Cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.bismark.onlineticket.R
import com.bismark.onlineticket.databinding.FragmentCartScreenBinding
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CartScreen : Fragment() {

    private var _binding: FragmentCartScreenBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: CartViewModelFactory
    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(CartViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCartScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkoutBtn.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}