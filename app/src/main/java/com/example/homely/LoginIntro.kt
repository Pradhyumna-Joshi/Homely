package com.example.homely

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.homely.R
import com.example.homely.databinding.FragmentLoginIntroBinding
import com.google.firebase.auth.FirebaseAuth


class LoginIntro : Fragment() {

    lateinit var firebaseAuth: FirebaseAuth
    private var binding_ : FragmentLoginIntroBinding?=null
    private val binding get() = binding_!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding_=FragmentLoginIntroBinding.inflate(layoutInflater,container,false)
        firebaseAuth= FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser!=null){
            redirect("MAIN")
        }

        binding.btnGetStarted.setOnClickListener{
            redirect("LOGIN")
        }

        return binding.root
    }


    private fun redirect(s:String){

        when(s){
            "LOGIN"->{
                findNavController().navigate(R.id.action_loginIntro_to_logInFragment)

            }
            "MAIN"->{
               findNavController().navigate(R.id.action_loginIntro_to_mainFragment)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding_=null
    }




}