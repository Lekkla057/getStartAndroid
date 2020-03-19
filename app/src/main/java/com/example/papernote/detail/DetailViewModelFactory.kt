package com.example.papernote.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.papernote.database.RecordDatabaseDao

class DetailViewModelFactory(
    private val title: Long,
    private val dataSource: RecordDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(title, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}