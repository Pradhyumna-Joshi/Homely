package com.example.homely

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homely.Adapters.*
import com.example.homely.ApiClient.ApiClient
import com.example.homely.ApiClient.ApiInterface
import com.example.homely.Models.Facilities
import com.example.homely.Models.Stay
import com.example.homely.databinding.FragmentTenantDashBoardBinding
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates


class Tenant_DashBoard : Fragment(), itemClicked {


    private var binding_ : FragmentTenantDashBoardBinding?=null
    private val binding get() = binding_!!


    private var retrofit = ApiClient.getclient()
    private var stayInstance = retrofit?.create(ApiInterface::class.java)


    private lateinit var adapter : TenantAdapter
    private lateinit var arrayList : ArrayList<Facilities>

    private  var typeRoom : Int = 0
    private  var typePG : Int = 0

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var userid : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_=FragmentTenantDashBoardBinding.inflate(layoutInflater,container,false)

        arrayList = ArrayList()


        firebaseAuth = FirebaseAuth.getInstance()
        userid=firebaseAuth.currentUser?.uid.toString()

        getData()

        binding.apply {




                if(firebaseAuth.currentUser?.displayName.toString()!=""){
                    user.setText(firebaseAuth.currentUser?.displayName)
                }
            else{
                    user.setText("User")
                }
            profile.setImageURI(firebaseAuth.currentUser?.photoUrl)
            cbRoom.setOnClickListener {
               if(cbRoom.isChecked){
                   typeRoom=1
               }
                else{
                   typeRoom=0

               }

                filterList(null,typeRoom,typePG)
            }
            cbPG.setOnClickListener {
                if(cbPG.isChecked){
                    typePG=1
                }
                else{
                    typePG=0

                }
                filterList(null,typeRoom,typePG)
            }






            searchView.clearFocus()
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    filterList(query,typeRoom,typePG)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
//                        Toast.makeText(requireContext(),newText,Toast.LENGTH_SHORT).show()
                        filterList(newText,typeRoom,typePG)
                    }
                    return true
                }

            })
        }


        return binding.root
    }

    private fun filterList(query: String?,typeRoom : Int,typePG : Int) {

            if(query == null && typeRoom == 0 && typePG==0){
                getData()
            }
            else{
                stayInstance?.searchStay(query,typeRoom,typePG)?.enqueue(object : Callback<Stay>{
                    override fun onResponse(call: Call<Stay>, response: Response<Stay>) {
                        if(response.body()?.status.equals("1")){
                            setAdapter(response.body()?.message!!)
                        }
                        else{
                            arrayList.clear()
                            setAdapter(arrayList)
                        }
                    }

                    override fun onFailure(call: Call<Stay>, t: Throwable) {
                        Log.d("SEARCH",t.localizedMessage.toString())
                    }

                })

            }
        }


    private fun getData() {
        stayInstance?.getStay1()?.enqueue(object : Callback<Stay> {
            override fun onResponse(call: Call<Stay>, response: Response<Stay>) {
                if(response!=null){
                    if(response.body()?.status.equals("1")){
                        arrayList = (response.body()?.message!!)
                        setAdapter(response.body()?.message!!)
                    }
                }
            }


            override fun onFailure(call: Call<Stay>, t: Throwable) {
                Toast.makeText(requireContext(),t.localizedMessage.toString(),Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun setAdapter(message: ArrayList<Facilities>) {

        adapter = TenantAdapter(requireContext(),message,this)
        binding.apply {
            recyclerView.layoutManager= LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onStayClicked(position: Int) {

//        Toast.makeText(requireContext(),"$position",Toast.LENGTH_SHORT).show()
        val bundle = Bundle()
        bundle.putParcelable("object", arrayList[position])
        val fragment = ItemStay()
        fragment.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.nav_host_controller,fragment)?.commit()
    }


}




