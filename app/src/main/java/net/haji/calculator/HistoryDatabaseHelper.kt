package net.haji.calculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HistoryDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "history_db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "history"
        private const val COLUMN_ID = "id"
        private const val COLUMN_EXPRESSION = "expression"
        private const val COLUMN_RESULT = "result"

        private var instance: HistoryDatabaseHelper? = null

        @Synchronized
        fun getInstance(context: Context): HistoryDatabaseHelper {
            if (instance == null) {
                instance = HistoryDatabaseHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_EXPRESSION TEXT,
                $COLUMN_RESULT TEXT
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // For simplicity, we just drop the table and recreate it (data will be lost on upgrade)
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insert history into the database
    fun insertHistory(expression: String, result: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EXPRESSION, expression)
            put(COLUMN_RESULT, result)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Retrieve history from the database
    @SuppressLint("Range")
    fun getHistory(): List<String> {
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_NAME, arrayOf(COLUMN_EXPRESSION, COLUMN_RESULT), null, null, null, null, "$COLUMN_ID DESC")
        val historyList = mutableListOf<String>()

        while (cursor.moveToNext()) {
            val expression = cursor.getString(cursor.getColumnIndex(COLUMN_EXPRESSION))
            val result = cursor.getString(cursor.getColumnIndex(COLUMN_RESULT))
            historyList.add("$expression = $result")
        }

        cursor.close()
        db.close()
        return historyList
    }
}
