package com.example.homely

import android.Manifest
import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Messenger
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.homely.ApiClient.ApiClient
import com.example.homely.ApiClient.ApiInterface
import com.example.homely.Models.Response
import com.example.homely.databinding.FragmentFacilitesBinding
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class Facilites : Fragment() {

    private var binding_: FragmentFacilitesBinding? = null
    private val binding get() = binding_!!
    private val retrofit = ApiClient.getclient()
    private val stayInstance = retrofit?.create(ApiInterface::class.java)


    protected open lateinit var name: String
    protected open lateinit var street: String
    protected open lateinit var city: String
    protected open lateinit var state: String
    protected open lateinit var landmark: String
    protected open lateinit var zip: String

    var imageURI = Uri.EMPTY
    lateinit var photoFile: File


    lateinit var firebaseAuth: FirebaseAuth
    lateinit var userid: String

    var imageURL : String =""


    var typeRoom: Int = 0
    var typePG: Int = 0

    var share1: Int = 0
    var share2: Int = 0
    var share3: Int = 0
    var share4: Int = 0


    var Fwifi: Int = 0
    var Fclean: Int = 0
    var Fwash: Int = 0
    var Ftv: Int = 0
    var Fveg: Int = 0
    var Fnonveg: Int = 0
    var Fnorth: Int = 0
    var FSouth: Int = 0


    var rent1: Int = 0
    var rent2: Int = 0
    var rent3: Int = 0
    var rent4: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding_ = FragmentFacilitesBinding.inflate(layoutInflater, container, false)



        binding.l2.visibility = View.GONE
        binding.l3.visibility = View.GONE
        binding.l4.visibility = View.GONE

        val bundle = this.requireArguments()
        name = bundle.getString("name").toString()
        street = bundle.getString("street").toString()
        city = bundle.getString("city").toString()
        state = bundle.getString("state").toString()
        landmark = bundle.getString("landmark").toString()
        zip = bundle.getString("zipcode").toString()

        firebaseAuth = FirebaseAuth.getInstance()
        userid = firebaseAuth.currentUser?.uid.toString()

        binding.apply {

            cbRoom.setOnClickListener {
                cbRoom.isChecked = !getIntValue(typeRoom)
                typeRoom = getBoolValue(cbRoom.isChecked)
            }

            cbPG.setOnClickListener {
                cbPG.isChecked = !getIntValue(typePG)
                typePG = getBoolValue(cbPG.isChecked)
            }

            cb1.setOnClickListener {
                cb1.isChecked = !getIntValue(share1)
                share1 = getBoolValue(cb1.isChecked)
            }

            cb2.setOnClickListener {
                cb2.isChecked = !getIntValue(share2)
                share2 = getBoolValue(cb2.isChecked)
                if (cb2.isChecked) {
                    l2.visibility = View.VISIBLE
                } else {
                    l2.visibility = View.GONE

                }

            }

            cb3.setOnClickListener {
                cb3.isChecked = !getIntValue(share3)
                share3 = getBoolValue(cb3.isChecked)
                if (cb3.isChecked) {
                    l3.visibility = View.VISIBLE
                } else {
                    l3.visibility = View.GONE

                }

            }

            cb4.setOnClickListener {
                cb4.isChecked = !getIntValue(share4)
                share4 = getBoolValue(cb4.isChecked)
                if (cb4.isChecked) {
                    l4.visibility = View.VISIBLE
                } else {
                    l4.visibility = View.GONE

                }

            }

            cbWifi.setOnClickListener {
                cbWifi.isChecked = !getIntValue(Fwifi)
                Fwifi = getBoolValue(cbWifi.isChecked)
            }


            cbClean.setOnClickListener {
                cbClean.isChecked = !getIntValue(Fclean)
                Fclean = getBoolValue(cbClean.isChecked)
            }

            cbVeg.setOnClickListener {
                cbVeg.isChecked = !getIntValue(Fveg)
                Fveg = getBoolValue(cbVeg.isChecked)
            }

            cbNonVeg.setOnClickListener {
                cbNonVeg.isChecked = !getIntValue(Fnonveg)
                Fnonveg = getBoolValue(cbNonVeg.isChecked)
            }

            cbWash.setOnClickListener {
                cbWash.isChecked = !getIntValue(Fwash)
                Fwash = getBoolValue(cbWash.isChecked)
            }

            cbTv.setOnClickListener {
                cbTv.isChecked = !getIntValue(Ftv)
                Ftv = getBoolValue(cbTv.isChecked)
            }

            cbNorth.setOnClickListener {
                cbNorth.isChecked = !getIntValue(Fnorth)
                Fnorth = getBoolValue(cbNorth.isChecked)
            }

            cbSouth.setOnClickListener {
                cbSouth.isChecked = !getIntValue(FSouth)
                FSouth = getBoolValue(cbSouth.isChecked)
            }

            binding.btnSubmit.setOnClickListener {
                getData()
            }

            binding.btnUploadImages.setOnClickListener {
                showChooserDialog()
            }




            return binding.root
        }


    }

    private fun getData() {

        binding.apply {


            rent1 = Integer.parseInt(etRent1.text.toString())


            if (cb2.isChecked) {
                rent2 = Integer.parseInt(etRent2.text.toString())
            }


            if (cb3.isChecked) {
                rent3 = Integer.parseInt(etRent3.text.toString())

            }

            if (cb4.isChecked) {
                rent4 = Integer.parseInt(etRent4.text.toString())

            }


        }
        imageURL=imageURI.toString()


        if (stayInstance != null) {
            stayInstance.addStay(
                name,
                street,
                city,
                state,
                landmark,
                zip,
                typeRoom,
                typePG,
                share1,
                share2,
                share3,
                share4,
                Fwifi,
                Fclean,
                Fveg,
                Fnonveg,
                Fnorth,
                FSouth,
                Fwash,
                Ftv,
                rent1,
                rent2,
                rent3,
                rent4,
                userid,
                imageURL
            ).enqueue(object :
                Callback<Response> {

                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    Toast.makeText(
                        requireContext(),
                        response.body()?.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    val dialog = Dialog(requireActivity())
                    dialog.setContentView(R.layout.custom_success_dialog)
                    dialog.setCancelable(false)
                    dialog.window
                        ?.setBackgroundDrawableResource(android.R.color.transparent)
                    dialog.show()
                    val window = dialog.window
                    window?.setLayout(
                        ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.WRAP_CONTENT
                    )

                    val dashboard: Button = dialog.findViewById(R.id.btnDashboard)


                    dashboard.setOnClickListener {
                        redirect()
                        dialog.dismiss()

                    }

                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        t.localizedMessage.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
        } else {
            Toast.makeText(requireContext(), "Here", Toast.LENGTH_LONG).show()
        }
    }

    private fun redirect() {

        val fragment = Owner_Dashboard()
        fragmentManager?.beginTransaction()?.replace(R.id.nav_host_controller, fragment)?.commit()

    }


    private fun showChooserDialog() {

        val dialog = Dialog(requireContext(), R.style.dialog)
        dialog.setContentView(R.layout.layout_chooser)
        dialog.show()

        dialog.findViewById<CardView>(R.id.Camera).setOnClickListener {

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (Build.VERSION.SDK_INT >= 19) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_DENIED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CAMERA),
                        1
                    )
                } else {

                    photoFile = createImageURI()
                    Log.d("MYDATA", photoFile.name.toString())
                    imageURI = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.homely.fileProvider",
                        photoFile
                    )




                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//
                    dialog.dismiss()

                }
            } else {

                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
                intent.setType("*/*");
                getCameraResult.launch(intent)

            }

        }

        dialog.findViewById<CardView>(R.id.Gallery).setOnClickListener {



//            var intent = Intent()
//            if (Build.VERSION.SDK_INT < 19) {
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                getResult.launch(intent)
//            } else {
//                intent = Intent(Intent.ACTION_OPEN_DOCUMENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("*/*");
//                getResult.launch(intent)

            val intent = Intent(Intent.ACTION_PICK)
//            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            getResult.launch(intent)
            dialog.dismiss()
        }



    }
    private fun createImageURI(): File {
        val imageDir =
            File(activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "camera_photos")

        if (!imageDir.exists()) {
            imageDir.mkdirs()
        }

        val fileName = SimpleDateFormat("yyyyddMM_HH:mm:ss").format(Date())
        val file = File(imageDir.path + File.separator + fileName)

        return file


    }

    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {

                val uri = it.data?.data!!
                binding.layoutImage.visibility = View.VISIBLE
                binding.imageStay.setImageURI(uri)
                imageURI = uri

            }
        }


    private val getCameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            binding.layoutImage.visibility = View.VISIBLE
            binding.imageStay.setImageURI(imageURI)

        }


    fun getBoolValue(cb: Boolean): Int {
        if (cb)
            return 1
        else return 0
    }


    fun getIntValue(value: Int): Boolean {
        return value == 1
    }



}







