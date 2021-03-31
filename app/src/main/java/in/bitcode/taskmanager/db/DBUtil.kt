package `in`.bitcode.taskmanager.db

import `in`.bitcode.taskmanager.models.Task
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBUtil(context: Context?) {

    private val mDb: SQLiteDatabase

    init {
        mDb = TaskDBHelper(context, "db_tasks", null, 1).writableDatabase
    }

    fun getAllTasks(): ArrayList<Task> {

        var listTasks: ArrayList<Task> = ArrayList<Task>()

        var columns = arrayOf("id", "title", "added_on", "status")

        var c = mDb.query("tasks", columns, null, null, null, null, "added_on desc")

        while (c.moveToNext()) {

            listTasks.add(
                Task(
                    c.getLong(0),
                    c.getString(1),
                    c.getLong(2),
                    (if (c.getInt(3) == 1) true else false)
                )

            )
        }

        return listTasks;
    }

    fun addTask(title: String, addedOn: Long, status: Boolean): Long {

        var cursor = mDb.rawQuery("select max(id)+1 as max_id from tasks", null)
        if(cursor.count == 0) {
            return -1.toLong()
        }

        cursor.moveToNext()
        var nextTaskId = cursor.getLong(cursor.getColumnIndex("max_id"))

        var values = ContentValues()
        values.put("id", nextTaskId)
        values.put("title", title)
        values.put("added_on", addedOn)
        values.put("status", status)

        if ( mDb.insert("tasks", null, values) != -1.toLong() )
            return nextTaskId

        return -1.toLong()
    }

    fun deleteTask(id: Long): Boolean? {
        return null
    }

    fun updateTask(id: Long, title: String, status: Boolean): Boolean? {
        return null
    }

    fun close(): Unit {
        mDb.close()
    }

    private class TaskDBHelper(
        context: Context?,
        dbName: String,
        cursorFactory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, dbName, cursorFactory, version) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL("create table tasks( id integer primary key, title text, added_on long, status integer)")

            var values = ContentValues()
            values.put("id", 1)
            values.put("title", "Demo Task")
            values.put("added_on", System.currentTimeMillis())
            values.put("status", false)
            db?.insert("tasks", null, values)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        }

    }

}