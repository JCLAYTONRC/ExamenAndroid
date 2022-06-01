package com.example.pruebaapi.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.pruebaapi.databinding.FragmentFotografiaBinding
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class FotografiaFragment : Fragment() {

    private lateinit var mBinding: FragmentFotografiaBinding
    lateinit var  ImageUri : Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentFotografiaBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnselectImage.setOnClickListener {

            imagenSeleccionada()        }

        mBinding.btnsubirImage.setOnClickListener {
            imagenaSubir()
        }
    }



    private fun imagenaSubir() {

        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Cargando archivo...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)

        val storageReference = FirebaseStorage.getInstance().getReference("image/$fileName")

        storageReference.putFile(ImageUri).
                addOnSuccessListener {

                    mBinding.imgFirebase.setImageURI(null)
                    Toast.makeText(context,"Se guardo exitosamente",Toast.LENGTH_SHORT).show()

                    if(progressDialog.isShowing) progressDialog.dismiss()

                }.addOnFailureListener{

            if(progressDialog.isShowing) progressDialog.dismiss()

            Toast.makeText(context,"Error al cargar",Toast.LENGTH_SHORT).show()

        }

    }

    private fun imagenSeleccionada() {

        val intent = Intent()
        intent.type="image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK){


            ImageUri = data?.data!!
            mBinding.imgFirebase.setImageURI(ImageUri)
        }
    }
}