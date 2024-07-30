package com.example.confiesionesdesanagustin.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.confiesionesdesanagustin.objects.Chapter
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class DatabaseHelper(private val mContext: Context) :
    SQLiteOpenHelper(mContext, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "confesiones2.db"
        private const val DB_VERSION = 1
    }

    private val dbPath: String = mContext.getDatabasePath(DB_NAME).path
    private var mDataBase: SQLiteDatabase? = null

    @Throws(IOException::class)
    fun createDatabase() {
        // Forzar la copia de la base de datos desde los assets
        this.readableDatabase
        this.close()
        try {
            copyDatabase()
        } catch (e: IOException) {
            throw Error("Error copying database")
        }
    }

    @Throws(IOException::class)
    private fun copyDatabase() {
        val input: InputStream = mContext.assets.open(DB_NAME)
        val output: OutputStream = FileOutputStream(dbPath)
        val buffer = ByteArray(1024)
        var length: Int
        while (input.read(buffer).also { length = it } > 0) {
            output.write(buffer, 0, length)
        }
        output.flush()
        output.close()
        input.close()
    }

    @Throws(SQLiteException::class)
    fun openDataBase(): SQLiteDatabase {
        mDataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY)
        return mDataBase as SQLiteDatabase
    }

    @Synchronized
    override fun close() {
        mDataBase?.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // No es necesario implementar esto ya que estamos usando una base de datos preexistente.
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // No es necesario implementar esto ya que estamos usando una base de datos preexistente.
    }

    fun getAllBooks(): List<Map<String, String?>> {
        val db = this.readableDatabase
        val dataList = mutableListOf<Map<String, String?>>()
        val columns = arrayOf("title", "description")
        val cursor = db.query("books", columns, null, null, null, null, null)
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val row = mutableMapOf<String, String?>()
                    row["title"] = it.getString(it.getColumnIndexOrThrow("title"))
                    row["description"] = it.getString(it.getColumnIndexOrThrow("description"))
                    dataList.add(row)
                } while (it.moveToNext())
            }
        }
        return dataList
    }

    /*fun getTableInfo(context: Context, tableName: String) {
        val dbHelper = DatabaseHelper(context)
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("PRAGMA table_info($tableName)", null)

        if (cursor.moveToFirst()) {
            do {
                val columnName = cursor.getString(1)
                val columnType = cursor.getString(2)
                Log.d("TableInfo", "Column: $columnName, Type: $columnType")
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
    }*/

    fun getChapters(pos: Number): List<Map<String, String?>> {
        val dataList = mutableListOf<Map<String, String?>>()
        val db = this.readableDatabase
        val cursor = db.query(
            "chapters", // Table name
            arrayOf("title", "description"), // Columns to return
            "id_book = ?", // Where clause
            arrayOf(pos.toString()), // Where clause arguments
            null, // Group by
            null, // Having
            null // Order by
        )
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val row = mutableMapOf<String, String?>()
                    row["title"] = it.getString(it.getColumnIndexOrThrow("title"))
                    row["description"] = it.getString(it.getColumnIndexOrThrow("description"))
                    dataList.add(row)
                } while (it.moveToNext())
            }
        }
        return dataList
    }

    fun getCompleteChapters(book: Number): List<Chapter> {
        val dataList = mutableListOf<Chapter>()
        val db = this.readableDatabase
        val cursor = db.query(
            "chapters",
            arrayOf("title", "description", "content"),
            "id_book = ?",
            arrayOf(book.toString()),
            null,
            null,
            null
        )
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val title = it.getString(it.getColumnIndexOrThrow("title"))
                    val desc = it.getString(it.getColumnIndexOrThrow("description"))
                    val cont = it.getString(it.getColumnIndexOrThrow("content"))
                    dataList.add(Chapter(title, desc, cont))
                } while (it.moveToNext())
            }
        }
        return dataList
    }
}
