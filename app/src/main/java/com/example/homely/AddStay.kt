package com.example.homely

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.homely.databinding.FragmentAddStayBinding


class AddStay : Fragment() {


    private var binding_: FragmentAddStayBinding? = null
    private val binding get() = binding_!!

    protected open lateinit var name: String
    protected open lateinit var street: String
    protected open lateinit var city: String
    protected open lateinit var landmark: String
    protected open lateinit var zip: String


    override fun onResume() {
        super.onResume()
        val statesList = resources.getStringArray(R.array.states)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item, statesList)
        binding.tvStates.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding_ = FragmentAddStayBinding.inflate(layoutInflater, container, false)


        binding.btnNext.setOnClickListener {

            val bundle : Bundle = Bundle()
            bundle.putString("name",  binding.etNameOfStay.text.toString())
            bundle.putString("street",  binding.etStreet.text.toString())
            bundle.putString("city",  binding.etCity.text.toString())
            bundle.putString("state",  binding.tvStates.text.toString())
            bundle.putString("landmark",  binding.etLandmark.text.toString())
            bundle.putString("zipcode",  binding.etPincode.text.toString())

            val fragment = Facilites()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_controller,fragment)?.commit()
        }
        return binding.root
    }

}