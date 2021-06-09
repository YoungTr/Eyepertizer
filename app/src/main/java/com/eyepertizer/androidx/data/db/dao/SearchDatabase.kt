package com.eyepertizer.androidx.data.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eyepertizer.androidx.data.db.model.SearchHistory

@Database(entities = [SearchHistory::class], version = 1)
abstract class SearchDatabase : RoomDatabase() {

    abstract fun searchDao(): SearchDao
}