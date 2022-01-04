package com.mean.traclock.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mean.traclock.TraclockApplication
import com.mean.traclock.database.AppDatabase
import com.mean.traclock.database.Record
import com.mean.traclock.util.getIntDate
import kotlin.concurrent.thread

class EditRecordViewModel(val record: Record) : ViewModel() {
    private val _project = MutableLiveData(record.project)
    private val _startTime = MutableLiveData(record.startTime)
    private val _endTime = MutableLiveData(record.endTime)
    private val _showDialog = MutableLiveData(false)

    val project: LiveData<String>
        get() = _project
    val startTime: LiveData<Long>
        get() = _startTime
    val endTime: LiveData<Long>
        get() = _endTime
    val showDialog: LiveData<Boolean>
        get() = _showDialog

    fun setShowDialog(showDialog: Boolean) {
        _showDialog.value = showDialog
    }

    fun isModified(): Boolean {
        return _project.value != record.project || _startTime.value != record.startTime || _endTime.value != record.endTime

    }

    fun updateRecord(): Int {
        if (isModified()) {
            if (_project.value.isNullOrBlank()) {
                return -1//项目名为空
            } else {
                return if (_project.value in TraclockApplication.projectsList) {
                    record.project = _project.value ?: ""
                    record.startTime = startTime.value ?: 0L
                    record.endTime = endTime.value ?: 0L
                    record.date = getIntDate(record.startTime)
                    thread {
                        AppDatabase.getDatabase(TraclockApplication.context)
                            .recordDao().update(record)
                    }
                    1
                } else {
                    -2//项目不存在
                }
            }
        } else {
            return 2//没有变化
        }
    }

    fun setProject(project: String) {
        _project.value = project
    }

    fun setStartTime(startTime: Long) {
        _startTime.value = startTime
    }

    fun setEndTime(endTime: Long) {
        _endTime.value = endTime
    }
}