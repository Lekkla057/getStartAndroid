package com.example.papernote.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.papernote.database.Record
import com.example.papernote.database.RecordDatabaseDao
import kotlinx.coroutines.*

class MainViewModel( val database: RecordDatabaseDao,
                     application: Application
) : AndroidViewModel(application) {
    val database2=database
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var nowRecord = MutableLiveData<Record?>()
     var allRecord = database.getAllRecord()
    private var _swipe = MutableLiveData<Boolean>()

    val swipe: LiveData<Boolean>
        get() = _swipe
    init {
        initializeTonight()
        startSwipe()
    }
    private fun initializeTonight() {
        uiScope.launch {
            nowRecord.value = getNowRecordFromDatabase()
        }
    }
    private suspend fun getNowRecordFromDatabase(): Record? {
        return withContext(Dispatchers.IO) {
            var Now = database.getNowRecord()
           // System.out.println("sssss"+allRecord)
            Now
        }

    }
    fun addTitle() {
        uiScope.launch {
            val newRecord = Record()
            insert(newRecord)
    }}
    private suspend fun insert(newRecord: Record) {
        withContext(Dispatchers.IO) {
            database.insert(newRecord)
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }
    fun StartShowingSnackbar() {
        _showSnackbarEvent.value = true
    }
    fun Insert(title:String) {
        uiScope.launch {
        val newRecord = Record()
            newRecord.title=title
        insert(newRecord)
    }
    }

    fun delete(id:Long){
        uiScope.launch {

            nowRecord.value = sendDelete(id)
        }

    }
    private suspend fun sendDelete(id:Long): Record? {
        return withContext(context = Dispatchers.IO) {
           database.deleteByNoteId(id)
            var Now = database.getNowRecord()

            Now
        }

    }
    fun editTitle(obj:Record,title: String){

        uiScope.launch {
            obj.title=title
            allRecord = UpdateTitle(obj)
        }

    }
    private suspend fun UpdateTitle(obj: Record): LiveData<List<Record>> {
        return withContext(context = Dispatchers.IO) {


            database.update(obj)
            var Now = database.getAllRecord()

            Now


        }

    }



    fun startSwipe(){

        _swipe.value=true
    }
    fun doneSwipe(){
        _swipe.value=false
    }
}