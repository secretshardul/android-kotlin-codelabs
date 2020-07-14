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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {
    // Database can have multiple DAOs
    abstract val sleepDatabaseDao: SleepDatabaseDao

    // Companion object- Allows method access without creating object. It is similar to 'static' in Java.
    companion object {
        @Volatile // INSTANCE should not be cached for consistent access during multi-threading.
        private var INSTANCE: SleepDatabase? = null // Holds reference to database, once created

        /**
         * Return database reference. If database does not exist, it is created.
         */
        fun getInstance(context: Context): SleepDatabase {

            synchronized(this) { // Only 1 thread gets access to database at a time for consistency

                var instance = INSTANCE
                if (instance == null) { // Build database if it does not exist
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            SleepDatabase::class.java,
                            "sleep_history_database"
                    ).fallbackToDestructiveMigration().build()
                    /** Migration strategy
                     * Needed during Room database creation. The simplest strategy is
                     * `fallbackToDestructiveMigration()` where existing data is destroyed.
                     */

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
