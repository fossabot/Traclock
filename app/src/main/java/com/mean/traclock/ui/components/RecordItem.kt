package com.mean.traclock.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mean.traclock.R
import com.mean.traclock.database.Record
import com.mean.traclock.ui.EditRecordActivity
import com.mean.traclock.ui.ProjectActivity
import com.mean.traclock.utils.Database
import com.mean.traclock.utils.HORIZONTAL_MARGIN
import com.mean.traclock.utils.TimingControl
import com.mean.traclock.utils.getDurationString
import com.mean.traclock.utils.getTimeString
import kotlinx.coroutines.DelicateCoroutinesApi

@SuppressLint("UnrememberedMutableState")
@OptIn(DelicateCoroutinesApi::class, ExperimentalFoundationApi::class)
@Composable
fun RecordItem(context: Context, record: Record, color: Color, detailView: Boolean = false) {
    val startTime = getTimeString(record.startTime)
    val endTime = getTimeString(record.endTime)
    val projectName = record.project
    var showMenu by remember { mutableStateOf(false) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {
                        val activity =
                            if (detailView) EditRecordActivity::class.java else ProjectActivity::class.java
                        val intent = Intent(context, activity)
                        if (detailView) {
                            putRecord(intent, record)
                        } else {
                            intent.putExtra("projectName", record.project)
                        }
                        context.startActivity(intent)
                    },
                    onLongClick = if (detailView) {
                        { showMenu = true }
                    } else null
                )
                .padding(vertical = 8.dp, horizontal = HORIZONTAL_MARGIN),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(8.dp, 0.dp)
                    )
                    Column {
                        Text(projectName)
                        if (detailView) {
                            Text(
                                "$startTime - $endTime",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                SmallOutlinedButton(
                    text = getDurationString(record.startTime, record.endTime, detailView),
                    onClick = { TimingControl.startRecord(projectName) }
                )
            }
            DropdownMenu(showMenu, { showMenu = false }) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.delete)) },
                    onClick = { showMenu = false; Database.deleteRecord(record) }
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecordItemWithoutProject(context: Context, record: Record) {
    val startTime = getTimeString(record.startTime)
    val endTime = getTimeString(record.endTime)
    var showMenu by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                enabled = true,
                onClick = {
                    val intent = Intent(context, EditRecordActivity::class.java)
                    putRecord(intent, record)
                    context.startActivity(intent)
                },
                onLongClick = { showMenu = true }
            )
            .padding(vertical = 24.dp, horizontal = HORIZONTAL_MARGIN),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("$startTime - $endTime")
            Text(getDurationString(record.startTime, record.endTime))
        }
        DropdownMenu(showMenu, { showMenu = false }) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.delete)) },
                onClick = { showMenu = false; Database.deleteRecord(record) }
            )
        }
    }
}

fun putRecord(intent: Intent, record: Record) {
    intent.putExtra("id", record.id)
    intent.putExtra("project", record.project)
    intent.putExtra("startTime", record.startTime)
    intent.putExtra("endTime", record.endTime)
    intent.putExtra("date", record.date)
}
