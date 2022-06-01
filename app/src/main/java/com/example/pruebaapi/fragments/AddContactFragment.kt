package com.example.pruebaapi.fragments

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.pruebaapi.MainActivity
import com.example.pruebaapi.R
import com.example.pruebaapi.database.EmpleadoAplicacion
import com.example.pruebaapi.database.EmpleadoEntity
import com.example.pruebaapi.databinding.FragmentAddContactBinding
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AddContactFragment : Fragment() {

    private lateinit var mBinding : FragmentAddContactBinding
    private  var mActivity : MainActivity? = null
    private var mFragment : AgendaFragment? = null
    lateinit var  ImageUri : Uri

    private lateinit var mAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentAddContactBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.agregar_empleado)

        setHasOptionsMenu(true)

        mBinding.imgEmpleado.setOnClickListener{
            imagenSeleccionada()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }
            R.id.action_save -> {

                guardarDatos()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun guardarDatos() {
        val empleado = EmpleadoEntity(nombre =mBinding.etNombreEmpleado.text.toString().trim(),
            NumeroEmpleado = mBinding.etNumeroEmpleado.text.toString(),
            telefono = mBinding.etTelefonoEmpleado.text.toString(),
            email = mBinding.etEmailEmpleado.text.toString(), direccion = mBinding.etDireccionEmpleado.text.toString())

        doAsync {
            EmpleadoAplicacion.database.empleadoDao().addEmpleado(empleado)
            uiThread {
                Snackbar.make(mBinding.root,getString(R.string.mensaje_exitoso_guardar_empleado),
                    Snackbar.LENGTH_SHORT).show()
            }
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

        if(requestCode == 100 && resultCode == Activity.RESULT_OK){


            ImageUri = data?.data!!
            mBinding.imgEmpleado.setImageURI(ImageUri)
        }
    }


    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title= getString(R.string.app_name)
        mFragment?.gonebutton(true)
        setHasOptionsMenu(false)
        super.onDestroy()
    }

}