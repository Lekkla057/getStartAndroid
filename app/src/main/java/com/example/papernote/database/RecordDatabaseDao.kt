package com.example.papernote.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface RecordDatabaseDao {

    @Insert
    fun insert(night: Record)

    @Update
    fun update(night: Record)

    @Query("SELECT * from daily_record_note WHERE noteId = :key")
    fun get(key: Long): Record?

    @Query("DELETE FROM daily_record_note")
    fun clear()

    @Query("SELECT * FROM daily_record_note ORDER BY noteId DESC LIMIT 1")
    fun getNowRecord(): Record?

    @Query("SELECT * FROM daily_record_note ORDER BY noteId DESC")
    fun getAllRecord(): LiveData<List<Record>>

    @Query("DELETE FROM daily_record_note WHERE noteId = :key")
    fun deleteByNoteId(key: Long)
}