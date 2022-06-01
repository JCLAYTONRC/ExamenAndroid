package com.example.pruebaapi

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.utils.widget.MockView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebaapi.databinding.ActivityMainBinding
import com.example.pruebaapi.fragments.AgendaFragment
import com.example.pruebaapi.fragments.FotografiaFragment
import com.example.pruebaapi.models.Movie
import com.example.pruebaapi.models.MovieResponse
import com.example.pruebaapi.services.MovieApiInterface
import com.example.pruebaapi.services.MovieApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val agendaFragment = AgendaFragment()
    private val fotografiaFragment = FotografiaFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        checarPermisos()



        rv_movie_list.layoutManager = LinearLayoutManager(this)
        rv_movie_list.setHasFixedSize(true)
        getMovieData { movies: List<Movie> ->
            rv_movie_list.adapter = MovieAdapter(movies)
        }

        mBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.agenda -> replaceFragment(agendaFragment)

                R.id.foto -> replaceFragment(fotografiaFragment)
            }
            true
        }
    }



    private fun checarPermisos() {
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){

                pedirpermisos()
        }else{

        }


    }

    private fun pedirpermisos() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)
            || ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.INTERNET)){
    }else{
        ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.INTERNET,Manifest.permission.READ_EXTERNAL_STORAGE),777)

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 777){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){

            }else{
                //Permiso denegado
            }
        }
    }

    private fun getMovieData(callback :(List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList().enqueue(object : Callback<MovieResponse>{

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

        })

    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.containerMain,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }


}