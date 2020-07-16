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
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import android.text.Spanned
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    // Scope- Run viewModelJob on main thread because UI has to be updated
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var tonight = MutableLiveData<SleepNight?>() // holds current night
    val nights = database.getAllNights()

    // Transform list into HTML formatted string for display
    val nightsString: LiveData<Spanned> = Transformations.map(nights) {
        formatNights(it, application.resources)
    }

    // Observe for change and navigate to sleep quality screen
    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()
    val navigateToSleepQuality: LiveData<SleepNight>
        get() = _navigateToSleepQuality

    init {
        initializeTonight()
    }

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    /** Button visibility options **/
    /** Start button- visible only if a night is not being tracked **/
    val startButtonVisible: LiveData<Boolean> = Transformations.map(tonight) {
        it == null
    }
    /** Stop button- visible only if night is being tracked **/
    val stopButtonVisible: LiveData<Boolean> = Transformations.map(tonight) {
        it != null
    }
    /** Clear button- visible only if night list is not empty **/
    val clearButtonVisible: LiveData<Boolean> = Transformations.map(nights) {
        it.isNotEmpty()
    }

    /** To display snackbar when clear button is pressed **/
    private val _showSnackbarEvent  = MutableLiveData<Boolean>()
    val showConfirmationSnackbar: LiveData<Boolean>
            get() = _showSnackbarEvent

    /** Reset after snackbar is displayed **/
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    /** Coroutine functions: They run coroutines on main thread because they update UI. **/

    /** Set tonight value. Called by constructor. **/
    private fun initializeTonight() {
        // Launch coroutine
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    /**
     * Function to track night
     *
     * - Called when start button is clicked.
     * - Create new night, insert it to database, fetch it back as `tonight`.
     */
    fun onStartTracking() {
        uiScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()

        }
    }

    /**
     * Function to stop tracking night
     *
     * - Called when stop button is clicked.
     * - Get `tonight` object, update its end time and update database.
     */
    fun onStopTracking() {
        uiScope.launch {
            // return@ specifies the function from which the statement returns, in nested functions
            val oldNight = tonight.value ?: return@launch //Return from launch function
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)

            _navigateToSleepQuality.value = oldNight // To navigate to sleep quality screen
        }
    }

    /**
     * Function to clear all night data
     *
     * Called when clear button is clicked.
     */
    fun onClear() {
        uiScope.launch {
            _showSnackbarEvent.value = true
            clear()
        }
    }

    /** Suspend functions: They run coroutines on IO thread because they don't update UI/  **/

    private suspend fun getTonightFromDatabase():SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()
            // Return non-null if night is still active, ie. end time is not yet assigned
            if(night?.startTimeMilli != night?.endTimeMilli) {
                night = null
            }
            night
        }
    }

    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    /**
     * Called when ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

