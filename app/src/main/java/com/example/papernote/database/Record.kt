package com.example.papernote.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "daily_record_note")
data class Record(
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "detail")
    var detail: String = "",

    @ColumnInfo(name = "date")
    var date: Long = System.currentTimeMillis()
)