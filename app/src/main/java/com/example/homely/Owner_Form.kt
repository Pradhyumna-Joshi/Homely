package com.example.homely

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.homely.ApiClient.ApiClient
import com.example.homely.ApiClient.ApiInterface
import com.example.homely.Models.Response
import com.example.homely.databinding.FragmentOwnerFormBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import java.text.SimpleDateFormat
import java.util.*

class Owner_Form : Fragment() {

    private var  binding_:FragmentOwnerFormBinding?=null
    private val binding get()=binding_!!
    val calender = Calendar.getInstance()
    private lateinit var firebaseAuth : FirebaseAuth
    private  val retrofit = ApiClient.getclient()
    private  val stayInstance = retrofit?.create(ApiInterface::class.java)
    lateinit var userid : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = FragmentOwnerFormBinding.inflate(layoutInflater,container,false)


        firebaseAuth = FirebaseAuth.getInstance()
        userid = firebaseAuth.currentUser?.uid.toString()
       val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->

           calender.set(Calendar.YEAR,year)
           calender.set(Calendar.MONTH,month)
           calender.set(Calendar.DAY_OF_MONTH,day)

           updateCalender()
       }

        binding.apply {
           btnSubmit.setOnClickListener {

                getData()

           }

            textInputLayoutDate.setEndIconOnClickListener {
                DatePickerDialog(requireContext(),dateSetListener,calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show()
            }

        }

        return binding.root
    }

    private fun updateCalender() {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        binding.etDOB.setText(sdf.format(calender.time))
    }

    private fun getData()  {
        val name : String
        val dob : String
        val email : String
        val phoneNumber : String
        val upiid : String

        binding.apply {

            name = etName.text.toString()
            dob = etDOB.text.toString()
            email = etEmail.text.toString()
            phoneNumber = etPhoneNumber.text.toString()
            upiid = etUPI.text.toString()


            if(name.isBlank() || dob.isBlank() || email.isBlank() || email.isBlank() || phoneNumber.isBlank() || upiid.isBlank()){
                Snackbar.make(requireView(),"*Fill in all the fields",Snackbar.LENGTH_LONG).show()
                return
            }

        }

        binding.progressBar.visibility=View.VISIBLE

        if(stayInstance!=null){
            binding.progressBar.visibility=View.INVISIBLE
            stayInstance.addOwner(name,dob,email,phoneNumber,userid,upiid).enqueue(object  : Callback<Response>{

                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    Toast.makeText(requireContext(),response.body()?.message.toString(),Toast.LENGTH_LONG).show()

                    findNavController().navigate(R.id.action_ownerForm_to_owner_Dashboard)
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(requireContext(),t.localizedMessage.toString(),Toast.LENGTH_LONG).show()
                }

            })
        }
        else{
            Toast.makeText(requireContext(),"Here",Toast.LENGTH_LONG).show()
        }
    }

}