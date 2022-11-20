package com.example.homely

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homely.Adapters.OnItemClicked
import com.example.homely.Adapters.OwnerAdapter
import com.example.homely.ApiClient.ApiClient
import com.example.homely.ApiClient.ApiInterface
import com.example.homely.Models.Facilities
import com.example.homely.Models.Stay
import com.example.homely.databinding.FragmentOwnerDashboardBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import kotlin.collections.ArrayList
import kotlin.properties.Delegates


class Owner_Dashboard : Fragment(), OnItemClicked {


    private var binding_: FragmentOwnerDashboardBinding? = null
    private val binding get() = binding_!!


    private var retrofit = ApiClient.getclient()
    private var stayInstance = retrofit?.create(ApiInterface::class.java)


    private lateinit var adapter: OwnerAdapter
    private lateinit var arrayList: ArrayList<Facilities>



    lateinit var userid: String
    private var itemStay: Facilities? = null
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        var position_ by Delegates.notNull<Int>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding_ = FragmentOwnerDashboardBinding.inflate(layoutInflater, container, false)



        firebaseAuth = FirebaseAuth.getInstance()
        userid = firebaseAuth.currentUser?.uid.toString()


        binding.btnAddStay.setOnClickListener {
            findNavController().navigate(R.id.action_owner_Dashboard_to_addStay)
        }
        arrayList = ArrayList()
        getData()




        return binding.root
    }

    private fun getData() {
        stayInstance?.getStay(userid)?.enqueue(object : Callback<Stay> {
            override fun onResponse(call: Call<Stay>, response: Response<Stay>) {
                if (response != null) {
                    if (response.body()?.status.equals("1")) {
                        arrayList = response.body()?.message!!
                        setAdapter(response.body()?.message!!)
                    }
                }
            }


            override fun onFailure(call: Call<Stay>, t: Throwable) {
//                Toast.makeText(requireContext(),t.localizedMessage.toString(),Toast.LENGTH_LONG).show()
                Log.d("TAG", t.localizedMessage.toString())
            }


        })
    }


    private fun setAdapter(message: ArrayList<Facilities>) {

        adapter = OwnerAdapter(requireContext(), message, this)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }

    }




    override fun onStayClicked(position: Int) {
       position_ = position
        val bundle = Bundle()
        bundle.putParcelable("object", arrayList[position])
        Log.d("DA2", arrayList[position].toString())
        val fragment = EditStay()
        fragment.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.nav_host_controller,fragment)?.commit()

    }




    override fun onDeleteClicked(position: Int) {

        Toast.makeText(requireContext(),"$position",Toast.LENGTH_SHORT).show()


        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.custom_dialog_delete)
        dialog.setCancelable(false)
        dialog.window
            ?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
        val window = dialog.window
        window?.setLayout(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )

        val btnYes: Button = dialog.findViewById(R.id.btnYes)

        val btnNo: Button = dialog.findViewById(R.id.btnNo)


        btnYes.setOnClickListener {
            stayInstance?.deleteStay(position+1)
                ?.enqueue(object : Callback<com.example.homely.Models.Response> {
                    override fun onResponse(
                        call: Call<com.example.homely.Models.Response>,
                        response: Response<com.example.homely.Models.Response>
                    ) {
                        Snackbar.make(requireView(),"${response.body()?.message}",Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                        setAdapter(arrayList)

                    }

                    override fun onFailure(
                        call: Call<com.example.homely.Models.Response>,
                        t: Throwable
                    ) {
                        Toast.makeText(
                            requireContext(),
                            t.localizedMessage.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
        }

        btnNo.setOnClickListener {
            dialog.dismiss()
        }


    }


}