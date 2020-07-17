/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = " https://android-kotlin-fun-mars-server.appspot.com/"
// Initialize retrofit with base URL
private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

/** API interface to access endpoints using HTTP method annotations(GET, POST, DELETE etc). **/
interface MarsApiService {
    @GET("realestate") // GET request to '/realestate' endpoint
    fun getProperties():
            Call<String> // Call object is used to start the request
}

/** Create object to initialize retrofit. This object is used to make API calls. **/
object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}
