package com.example.homely

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.homely.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    lateinit var firebaseAuth: FirebaseAuth
    private var binding_ : FragmentSignInBinding?=null
    private val binding get() = binding_!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding_ = FragmentSignInBinding.inflate(layoutInflater,container,false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.apply {

            etEmail.requestFocus()

            btnSignIn.setOnClickListener {
                signIn()
            }


            tvLogin.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
            }

        }

        return binding.root
    }

    private fun signIn(){
        binding.apply{

            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            //Test case possible
            if(username.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(requireContext(),"Username/Email/Password cannot be empty",Toast.LENGTH_LONG).show()
                return
            }

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if(it.isSuccessful){
                    findNavController().navigate(R.id.action_signInFragment_to_mainFragment)

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