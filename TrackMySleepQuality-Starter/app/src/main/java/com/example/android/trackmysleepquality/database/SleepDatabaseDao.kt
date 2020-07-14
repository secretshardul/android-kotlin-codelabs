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

package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepDatabaseDao {
    @Insert
    fun insert(night: SleepNight)

    /** Update night with new values
     * Entity with same primary key is updated
     */
    @Update
    fun update(night: SleepNight)

    /** Get night based on provided nightId
     * Null can be returned if night does not exist.
     */
    @Query("SELECT * from daily_sleep_quality_table where nightId = :key")
    fun get(key: Long): SleepNight?

    /** Empty table without deleting it
     * Query is used because @Delete annotation only allows single item deletion.
     */
    @Query("DELETE from daily_sleep_quality_table") // Empty the table
    fun clear()


    /** Return LiveData list of all nights in descending order of nightId
     * - Descending for latest to oldest night order
     * - LiveData object is returned. Room automatically keeps LiveData updated
     */
    @Query("SELECT * from daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>

    /** Return latest night
     * nightId has highest value for latest night. To get latest night, sort in descending order
     * and get first element.
     */
    @Query("SELECT * from daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight(): SleepNight?
}
