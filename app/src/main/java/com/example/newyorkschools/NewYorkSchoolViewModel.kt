package com.example.newyorkschools

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newyorkschools.model.NewYorkSchoolItem
import com.example.newyorkschools.model.NewYorkSchools
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import com.example.newyorkschools.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class NewYorkSchoolViewModel @Inject constructor( private val repository: SchoolRepository): ViewModel(){
        private val _schoolsFlow = MutableStateFlow<NewYorkSchools>(NewYorkSchools())
        val schoolsFlow = _schoolsFlow.asStateFlow()

        private val _schoolsDetailFlow = MutableStateFlow<NewYorkSchools>(NewYorkSchools())
        val schoolsDetailFlow = _schoolsDetailFlow.asStateFlow()
        init {
            getSchools()
        }
        fun getSchools(){
                viewModelScope.launch(Dispatchers.IO) {
                        try {
                                when (val result = repository.getSchools(data = "https://data.cityofnewyork.us/resource/s3k6-pzi2.json")) {
                                        is Result.Success -> {
                                                // Update UI with the fetched data
                                                _schoolsFlow.value = result.data
                                                Log.d("Schooldata", "getSchools: ${schoolsFlow.value}")

                                        }
                                        is Result.Error -> {
                                                // Handle the error, e.g., show an error message
                                                Log.d("Error!!", "fetchQuestions: ${result.exception.message}")
                                        }
                                        is Result.ApiError -> {
                                                Log.d("API Error", "fetchQuestions: ${result.message} with code: ${result.code} ")
                                        }
                                }





                        } catch (e: IOException) {
                                e.printStackTrace()
                                null
                        }
                }
        }
        fun onClick(){
                viewModelScope.launch {
                        try {
                                when (val result = repository.getSchools(data = "https://data.cityofnewyork.us/resource/f9bf-2cp4.json")) {
                                        is Result.Success -> {
                                                // Update UI with the fetched data
                                                _schoolsFlow.value = result.data
                                                Log.d("Schooldata", "getSchools: ${schoolsFlow.value}")

                                        }
                                        is Result.Error -> {
                                                // Handle the error, e.g., show an error message
                                                Log.d("Error!!", "fetchQuestions: ${result.exception.message}")
                                        }
                                        is Result.ApiError -> {
                                                Log.d("API Error", "fetchQuestions: ${result.message} with code: ${result.code} ")
                                        }
                                }





                        } catch (e: IOException) {
                                e.printStackTrace()
                                null
                        }
//                        openScreen()
                }
        }
}