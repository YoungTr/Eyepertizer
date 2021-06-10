package com.eyepertizer.androidx.data.db.dao.repository.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistory(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "time") val time: Long,
)