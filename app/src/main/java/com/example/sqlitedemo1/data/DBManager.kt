package com.example.sqlitedemo1.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlitedemo1.model.Student

class DBManager : SQLiteOpenHelper {

    var context: Context
    private var db: SQLiteDatabase

    constructor(context: Context) : super(context, DATABASE_NAME, null, VERSION) {
        this.context = context
        db = this.writableDatabase
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlquery = "CREATE TABLE students (\n" +
                "    students_id      INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                             NOT NULL,\n" +
                "    students_name    TEXT    NOT NULL,\n" +
                "    students_phone   TEXT    NOT NULL,\n" +
                "    students_address TEXT    NOT NULL,\n" +
                "    students_email   TEXT    NOT NULL,\n" +
                "    students_image   BLOB    NOT NULL\n" +
                ");"
        db!!.execSQL(sqlquery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addStudents(student: Student) {
        //val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, student.mName)
        values.put(PHONE, student.mPhone)
        values.put(ADDRESS, student.mAddress)
        values.put(EMAIL, student.mEmail)
        values.put(IMAGE, student.mImage)
        db.insert(TABLE_NAME, null, values)
        //db.close()
    }

    fun getAllStudents(): ArrayList<Student> {
        //val db = this.readableDatabase
        val arrST = ArrayList<Student>()
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val student = Student()
                student.mID = cursor.getInt(0)
                student.mName = cursor.getString(1)
                student.mPhone = cursor.getString(2)
                student.mAddress = cursor.getString(3)
                student.mEmail = cursor.getString(4)
                student.mImage = cursor.getBlob(5)
                arrST.add(student)
            } while (cursor.moveToNext())
        }
        //db.close()
        return arrST
    }

    fun updateStudent(student: Student) {
        //val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, student.mName)
        values.put(PHONE, student.mPhone)
        values.put(ADDRESS, student.mAddress)
        values.put(EMAIL, student.mEmail)
        values.put(IMAGE, student.mImage)
        db.update(TABLE_NAME, values, "$ID=?", arrayOf(student.mID.toString()))
        //db.close()
    }

    fun deleteStudent(id: Int) {
        //val db = this.writableDatabase
        db.delete(TABLE_NAME, "$ID=? AND $NAME=?", arrayOf(id.toString(), "3"))
        //db.close()
    }

    fun getAllStudentsProvider(
        projection: Array<String>?, //columns trong table vd: arrayOf("students_address")
        selection: String?, // Điều kiện where vd: "students_id=?"
        selectionArgs: Array<String>?, // Gía trị điều kiện where vd: arrayOf("3") có thể hiểu là students_id=3
        orderBy: String? // Sắp xếp
    ): Cursor {
        return db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy)
    }

    companion object {
        val DATABASE_NAME = "db_students"
        val TABLE_NAME = "students"
        val ID = "students_id"
        val NAME = "students_name"
        val PHONE = "students_phone"
        val ADDRESS = "students_address"
        val EMAIL = "students_email"
        val IMAGE = "students_image"
        val VERSION = 1
    }
}
