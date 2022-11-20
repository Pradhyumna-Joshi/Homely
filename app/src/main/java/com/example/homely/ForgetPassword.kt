package com.example.homely

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.homely.databinding.FragmentForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth


class ForgetPassword : Fragment() {

    private var binding_:FragmentForgetPasswordBinding?=null
    private val binding get() = binding_!!

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var email : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding_ = FragmentForgetPasswordBinding.inflate(layoutInflater,container,false)
        firebaseAuth=FirebaseAuth.getInstance()
        binding.apply {

            etEmail.requestFocus()
            btnReset.setOnClickListener {
                validateData()
            }
        }

        return binding.root
    }

    private fun validateData() {

        email = binding.etEmail.text.toString()

        if(email.isBlank()){
            binding.etEmail.error = "Required"
            return
        }
        else{
            forgotPassword()
        }
    }

    private fun forgotPassword() {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(requireContext(),"Check your mail to reset your password",Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_forgetPassword_to_loginFragment)
            }
            else{
                Toast.makeText(requireContext(),it.exception!!.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }


}