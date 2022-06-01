package com.example.pruebaapi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import com.example.pruebaapi.R
import com.example.pruebaapi.database.EmpleadoAplicacion
import com.example.pruebaapi.databinding.FragmentAgendaBinding
import com.example.pruebaapi.fragments.auxiliares.MainAux
import kotlinx.coroutines.flow.combine
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AgendaFragment : Fragment(), MainAux {

    private lateinit var mBinding: FragmentAgendaBinding
    private lateinit var mAdapter: ContactAdapter
    val fragmentAddContactFragment = AddContactFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAgendaBinding.inflate(inflater,container,false)
        setUpRecycler()

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        mBinding.btnCrearContact.setOnClickListener { replacefragment(fragmentAddContactFragment) }
    }

    private fun setUpRecycler() {
        mAdapter = ContactAdapter(mutableListOf())
        getEmpleados()
        mBinding.rvagenda.apply {
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun getEmpleados(){
        doAsync {
            val empleados = EmpleadoAplicacion.database.empleadoDao().getAllEmpleado()
            uiThread {
                mAdapter.setEmpleado(empleados)
            }
        }
    }

    private fun replacefragment(fragment: Fragment) {

        if(fragment != null){
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.containerFragmentAgenda,fragment)
            transaction.addToBackStack(null)
            transaction.commit()

            gonebutton(false)
        }
    }

    override fun gonebutton(isVisible: Boolean) {
        if(isVisible) mBinding.btnCrearContact.visibility = View.VISIBLE else mBinding.btnCrearContact.visibility = View.GONE
    }


}