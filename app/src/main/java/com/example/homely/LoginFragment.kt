package com.example.homely

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.homely.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    lateinit var firebaseAuth:FirebaseAuth
    private var binding_ : FragmentLoginBinding?= null
    private val binding get() = binding_!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment

        binding_ = FragmentLoginBinding.inflate(layoutInflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.apply {

            etEmail.requestFocus()
            btnLogin.setOnClickListener {
               login()
            }


            tvSignup.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signInFragment)
            }

            tvForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_forgetPassword)
            }
        }


        return binding.root
    }

    private fun login(){

        binding.apply{

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()


            //Test case possible
            if(email.isBlank() || password.isBlank()){
                Toast.makeText(requireContext(),"Email/Password cannot be empty",Toast.LENGTH_LONG).show()
                return
            }

            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{

                if(it.isSuccessful){
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                else{
                    Toast.makeText(requireContext(),it.exception?.localizedMessage.toString(),Toast.LENGTH_LONG).show()

                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding_=null
    }


}