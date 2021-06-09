package com.eyepertizer.androidx.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistory(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "value") val value: String
)