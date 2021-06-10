package com.eyepertizer.androidx.data.db.dao.repository.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistory(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "value") val value: String
)