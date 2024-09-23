package com.example.newyorkschools

import android.util.Log
import com.example.newyorkschools.model.NewYorkSchoolItem
import com.example.newyorkschools.model.NewYorkSchools
import com.example.newyorkschools.model.Result
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class SchoolRepository @Inject constructor(private val client: OkHttpClient): ApiInterface {
    override suspend fun getSchools(data : String): Result<NewYorkSchools> {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val listType = Types.newParameterizedType(List::class.java, NewYorkSchoolItem::class.java)

        val jsonAdapter : JsonAdapter<NewYorkSchools> = moshi.adapter(listType)

        val request = Request.Builder()
            .url(data).build()


        return try {

            val response = client.newCall(request).execute()

            if(response.isSuccessful) {
                val json = response.body().string()
                val data = json?.let { jsonAdapter.fromJson(it) }!!

                Result.Success(data = data)
            }
            else{
                Result.ApiError(message = "failed",code = response.code())
                }

        }
        catch (e: Exception){
            Log.e("Exception", "getSchools: $e", )
            Result.Error(exception = e)
        }


    }

}