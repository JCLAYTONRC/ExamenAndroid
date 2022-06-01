package com.example.pruebaapi.services

import com.example.pruebaapi.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieApiInterface {

    @GET("/3/movie/popular?api_key=b6f4f5df2214159da873cd8721cdf7ab")

    fun getMovieList() : Call<MovieResponse>
}