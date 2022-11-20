package com.example.homely

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.homely.Adapters.TenantAdapter
import com.example.homely.ApiClient.ApiClient
import com.example.homely.ApiClient.ApiInterface
import com.example.homely.Models.Facilities
import com.example.homely.Models.Owner
import com.example.homely.Models.getOwner
import com.example.homely.databinding.FragmentMainBinding
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : Fragment() {

    private var binding_ : FragmentMainBinding?=null
    private val binding get() = binding_!!





    private var retrofit = ApiClient.getclient()
    private var stayInstance = retrofit?.create(ApiInterface::class.java)


    private lateinit var adapter: TenantAdapter
    private lateinit var arrayList: ArrayList<Facilities>

    private var itemStay: Facilities? = null
    private var ownerObject: Owner? = null


    lateinit var firebaseAuth: FirebaseAuth
    lateinit var userid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding_= FragmentMainBinding.inflate(layoutInflater,container,false)


        firebaseAuth=FirebaseAuth.getInstance()
        userid=firebaseAuth.currentUser?.uid.toString()

        binding.apply {


            tvTenant.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_tenant_DashBoard)
            }

            tvOwner.setOnClickListener{
                    getOwnerObject()
            }
        }





        return binding.root
    }

    private fun getOwnerObject() {
        if(stayInstance!=null){
            stayInstance?.getOwner(userid)?.enqueue(object  : Callback<getOwner> {
                override fun onResponse(call: Call<getOwner>, response: Response<getOwner>) {

                    if(response.body()?.status.equals("0")){
                        findNavController().navigate(R.id.action_mainFragment_to_owner_form)

                    }
                    else{
                        findNavController().navigate(R.id.action_mainFragment_to_owner_Dashboard)

                    }
//
                }

                override fun onFailure(call: Call<getOwner>, t: Throwable) {
                    Toast.makeText(requireContext(),t.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                }

            })
        }
        else{
            Toast.makeText(requireContext(),"here32", Toast.LENGTH_SHORT).show()

        }
    }



}