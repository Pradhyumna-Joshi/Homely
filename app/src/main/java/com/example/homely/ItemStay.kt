package com.example.homely

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.homely.Adapters.TenantAdapter
import com.example.homely.ApiClient.ApiClient
import com.example.homely.ApiClient.ApiInterface
import com.example.homely.Models.Facilities
import com.example.homely.Models.Owner
import com.example.homely.Models.getOwner
import com.example.homely.databinding.FragmentItemStayBinding
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ItemStay : Fragment() {

    private val GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"


    private var binding_: FragmentItemStayBinding? = null
    private val binding get() = binding_!!


    private var retrofit = ApiClient.getclient()
    private var stayInstance = retrofit?.create(ApiInterface::class.java)


    private lateinit var adapter: TenantAdapter
    private lateinit var arrayList: ArrayList<Facilities>

    private var itemStay: Facilities? = null
    private var ownerObject: Owner? = null


    lateinit var firebaseAuth: FirebaseAuth
    lateinit var userid: String

    var isAppInstalled: Boolean = false


    var rd1 : Boolean =false
    var rd2 : Boolean =false
    var rd3 : Boolean =false
    var rd4 : Boolean =false

    var totalAmount =0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding_ = FragmentItemStayBinding.inflate(layoutInflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        userid = firebaseAuth.currentUser?.uid.toString()

        val bundle = this.arguments
        itemStay = bundle?.getParcelable("object")


        getOwnerObject()

        binding.apply {

            l2.visibility=View.GONE
            l3.visibility=View.GONE
            l4.visibility=View.GONE

            tvName.text = itemStay?.name.toString()
            tvAddress.text = itemStay?.street + ",\n" + itemStay?.city + ",\n" + itemStay?.state
            tvLandmark.text = itemStay?.landmark.toString()
            tvzipCode.text = itemStay?.zipcode.toString()

            cbWifi.isChecked = getIntValue(itemStay?.wifi!!)
            cbClean.isChecked = getIntValue(itemStay?.cleaning!!)
            cbVeg.isChecked = getIntValue(itemStay?.veg!!)
            cbNonVeg.isChecked = getIntValue(itemStay?.nonVeg!!)
            cbNorth.isChecked = getIntValue(itemStay?.north!!)
            cbSouth.isChecked = getIntValue(itemStay?.south!!)
            cbWash.isChecked = getIntValue(itemStay?.wash!!)
            cbTv.isChecked = getIntValue(itemStay?.TV!!)



            tvOwnerName.setText(ownerObject?.name)
            tvEmail.setText(ownerObject?.email)
            roundImage.setImageURI(Uri.parse(itemStay!!.imageURL.toString()))


            cb1.isChecked = itemStay!!.share1 == 1

            radio1.setOnClickListener {
                radio1.isChecked = !rd1
                rd1 =  radio1.isChecked
                if(!rd1){
                    totalAmount-=Integer.parseInt(itemStay!!.rent1.toString())
                }
                else{
                    totalAmount+=Integer.parseInt(itemStay!!.rent1.toString())
                }
            }

            radio2.setOnClickListener {
                radio2.isChecked = !rd2
                rd2 =  radio2.isChecked
                if(!rd2){
                    totalAmount-=Integer.parseInt(itemStay!!.rent2.toString())
                }
                else{
                    totalAmount+=Integer.parseInt(itemStay!!.rent2.toString())

                }

            }

            radio3.setOnClickListener {
                radio3.isChecked = !rd3
                rd3 =  radio3.isChecked
                if(!rd3){
                    totalAmount-=Integer.parseInt(itemStay!!.rent3.toString())
                }
                else{
                    totalAmount+=Integer.parseInt(itemStay!!.rent3.toString())

                }

            }

            radio4.setOnClickListener {
                radio4.isChecked = !rd4
                rd4 =  radio4.isChecked
                if(!rd4){
                    totalAmount-=Integer.parseInt(itemStay!!.rent4.toString())
                }
                else{
                    totalAmount+=Integer.parseInt(itemStay!!.rent4.toString())

                }
            }



            if(itemStay!!.share2 == 1){
                l2.visibility=View.VISIBLE
                cb2.isChecked = itemStay!!.share2 == 1
            }
            else{
                l2.visibility=View.GONE
            }

            if(itemStay!!.share3 == 1){
                l3.visibility=View.VISIBLE
                cb3.isChecked = itemStay!!.share3 == 1
            }
            else{
                l3.visibility=View.GONE
            }

            if(itemStay!!.share4 == 1){
                l4.visibility=View.VISIBLE
                cb4.isChecked = itemStay!!.share4 == 1
            }
            else{
                l4.visibility=View.GONE
            }

            cb3.isChecked = itemStay!!.share3 == 1
            cb4.isChecked = itemStay!!.share4 == 1

            etRent1.setText(itemStay!!.rent1.toString())
            etRent2.setText(itemStay!!.rent2.toString())
            etRent3.setText(itemStay!!.rent3.toString())
            etRent4.setText(itemStay!!.rent4.toString())

            btnwhatsapp.setOnClickListener {

                isAppInstalled = getAppStatus("com.whatsapp")

                if(true){

                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse("https://api.whatsapp.com/send?phone="+"+91"+ownerObject?.phoneNumber))
                    startActivity(intent)
                }
                else{
                    Toast.makeText(requireContext(),"WhatsApp is not installed not installed on android device",Toast.LENGTH_SHORT).show()
                }

            }

            btnGPAY.setOnClickListener{




                Toast.makeText(requireContext(),"{$totalAmount}",Toast.LENGTH_SHORT).show()

                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(uri);
                intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
                getResult.launch(intent)

            }

        }
        return binding.root
    }

    private fun getAppStatus(url: String): Boolean {


        val packageManager = context?.packageManager!!
        var isInstalled : Boolean
        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES)
            isInstalled = true
        } catch (e: Exception) {
            isInstalled = false
        }
        return isInstalled
    }

    private fun getOwnerObject() {
        if(stayInstance!=null){
            stayInstance?.getOwner(userid)?.enqueue(object  : Callback<getOwner>{
                override fun onResponse(call: Call<getOwner>, response: Response<getOwner>) {
                    Log.d("DF",response.body()?.message.toString())
                    ownerObject = response.body()?.message?.get(0)
                    binding.tvOwnerName.setText("Name : " + response.body()?.message?.get(0)?.name)
                    binding.tvEmail.setText("Email : " +response.body()?.message?.get(0)?.email)
                }

                override fun onFailure(call: Call<getOwner>, t: Throwable) {
                    Toast.makeText(requireContext(),t.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
                }

            })
        }
        else{
            Toast.makeText(requireContext(),"here32",Toast.LENGTH_SHORT).show()

        }
    }

    fun getIntValue(value: Int): Boolean {
        return value == 1
    }


    var uri = Uri.Builder()
        .scheme("upi")
        .authority("pay")
        .appendQueryParameter("pa", "your-merchant-vap@xxx") // virtual ID
        .appendQueryParameter("pn", ownerObject?.name) // name
        .appendQueryParameter("mc", "your-merchant-code") // optional
        .appendQueryParameter("tr", "your-transaction-ref-id") // optional
        .appendQueryParameter("tn", "your-transaction-note") // any note about payment
        .appendQueryParameter("am", "your-order-amount") // amount
        .appendQueryParameter("cu", "INR") // currency
        .appendQueryParameter("url", "your-transaction-url") // optional
        .build()






    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                Toast.makeText(requireContext(),"Success",Toast.LENGTH_SHORT).show()
            }
        else{
                Toast.makeText(requireContext(),it.data.toString(),Toast.LENGTH_SHORT).show()

            }
    }





}