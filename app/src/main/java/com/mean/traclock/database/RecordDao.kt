package com.mean.traclock.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert
    fun insert(record: Record)

    @Delete
    fun delete(record: Record)

    @Update
    fun update(record: Record)

    @Query("UPDATE Record SET project = :newProject WHERE project = :oldProject")
    fun update(oldProject: String, newProject: String)

    @Query("DELETE FROM Record WHERE project = :projectName")
    fun deleteByProject(projectName: String)

    @Query("SELECT *, rowid FROM Record ORDER BY startTime DESC")
    fun getAll(): Flow<List<Record>>

    @Query("SELECT *, rowid FROM Record ORDER BY startTime DESC")
    fun getRecordsList(): List<Record>

    @Query("SELECT *, rowid FROM Record WHERE project = :projectName ORDER BY startTime DESC")
    fun getAll(projectName: String): Flow<List<Record>>

    @Query("SELECT project, 0 AS startTime, SUM(endTime - startTime)/1000 AS endTime, 0 AS date ,rowid FROM Record GROUP BY project")
    fun getProjectsTime(): Flow<List<Record>>

    @Query("SELECT date, SUM(endTime - startTime)/1000 AS time FROM Record GROUP BY date")
    fun getTimeByDate(): Flow<List<TimeByDate>>

    @Query("SELECT date, SUM(endTime - startTime)/1000 AS time FROM Record WHERE project = :project GROUP BY date")
    fun getTimeByDate(project: String): Flow<List<TimeByDate>>

    @Query("SELECT project, 0 AS startTime, SUM(endTime - startTime)/1000 AS endTime, date, rowid FROM Record GROUP BY project, date ORDER BY date DESC")
    fun getProjectsTimeByDate(): Flow<List<Record>>

    @Query("SELECT *, rowid FROM Record WHERE date=:date")
    fun getRecordsOfDate(date: Int): Flow<List<Record>>

    @Query("SELECT project, 0 AS startTime, SUM(endTime - startTime) AS endTime, date, rowid FROM Record WHERE date=:date GROUP BY project")
    fun getProjectsTimeOfDate(date: Int): Flow<List<Record>>
}
