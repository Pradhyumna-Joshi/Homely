package com.example.homely

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.example.homely.ApiClient.ApiClient
import com.example.homely.ApiClient.ApiInterface
import com.example.homely.Models.Facilities
import com.example.homely.Models.Response
import com.example.homely.databinding.FragmentEditStayBinding
import com.example.homely.databinding.FragmentOwnerFormBinding
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import java.util.*



class EditStay : Fragment() {


    override fun onResume() {
        super.onResume()
        val statesList = resources.getStringArray(R.array.states)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, statesList)
       binding.tvStates.setAdapter(arrayAdapter)
    }


    private var  binding_: FragmentEditStayBinding?=null
    private val binding get()=binding_!!
    val calender = Calendar.getInstance()
    private lateinit var firebaseAuth : FirebaseAuth
    private  val retrofit = ApiClient.getclient()
    private  val stayInstance = retrofit?.create(ApiInterface::class.java)
    lateinit var userid : String
    private  var itemStay : Facilities?= null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding_=FragmentEditStayBinding.inflate(layoutInflater,container,false)



        val bundle = this.arguments
        itemStay = bundle?.getParcelable("object")


        itemStay?.toString()?.let { Log.d("DA1", it) }


        binding.apply {

            etNameOfStay.setText(itemStay?.name)
            etCity.setText(itemStay?.city)
            tvStates.setText(itemStay?.state)
            etStreet.setText(itemStay?.street)
            etLandmark.setText(itemStay?.landmark)
            etPincode.setText(itemStay?.zipcode)



            binding.btnNext.setOnClickListener {
               itemStay?.name =  binding.etNameOfStay.text.toString()
              itemStay?.street=binding.etStreet.text.toString()
               itemStay?.city=binding.etCity.text.toString()
                itemStay?.state = binding.tvStates.text.toString()
               itemStay?.landmark = binding.etLandmark.text.toString()
                itemStay?.zipcode = binding.etPincode.text.toString()
//
//
                bundle?.putParcelable("object",itemStay)
                val fragment = editFacilites()
                fragment.arguments = bundle
                fragmentManager?.beginTransaction()?.replace(R.id.nav_host_controller,fragment)?.commit()
            }
        }



        return binding.root
    }




}