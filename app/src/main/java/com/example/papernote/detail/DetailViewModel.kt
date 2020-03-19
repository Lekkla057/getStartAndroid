package com.example.papernote.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.papernote.database.Record
import com.example.papernote.database.RecordDatabaseDao
import kotlinx.coroutines.*

class DetailViewModel(
    private val title: Long = 0L,
    val database: RecordDatabaseDao) : ViewModel() {
    private var nowRecord = MutableLiveData<Record?>()
    val data: LiveData<Record?>
        get() = nowRecord
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    init {
        initializeTonight()
    }
    private fun initializeTonight() {
        uiScope.launch {
            nowRecord.value = getNowRecordFromDatabase()
            System.out.println(nowRecord.value)
        }
    }
    private suspend fun getNowRecordFromDatabase(): Record? {
        return withContext(Dispatchers.IO) {
            var Now:Record
            if (title==-1.toLong()){
                System.out.println(title)
                     Now = database.getNowRecord()!!}
            else{
                System.out.println(title)
                Now = database.get(title)!!}


            Now
        }

    }
    fun updateDetail(text:String){
        uiScope.launch {
        withContext(Dispatchers.IO) {
            val tonight = nowRecord.value?.noteId?.let { database.get(it) } ?: return@withContext
            tonight.detail = text
            database.update(tonight)
        }

    }}
}