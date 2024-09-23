package com.example.newyorkschools

import com.example.newyorkschools.model.NewYorkSchools
import com.example.newyorkschools.model.Result


interface ApiInterface{
    suspend fun getSchools(data: String): Result<NewYorkSchools>
}