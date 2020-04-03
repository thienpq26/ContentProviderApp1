package com.example.sqlitedemo1

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlitedemo1.adapter.StudentsAdapter
import com.example.sqlitedemo1.data.DBManager
import com.example.sqlitedemo1.model.Student
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val PICK_IMAGE: Int = 123
    lateinit var arrST: ArrayList<Student>
    lateinit var dbManager: DBManager
    var position: Int = -1
    var size = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbManager = DBManager(this)
        arrST = ArrayList()

        if (dbManager.getAllStudents().isNotEmpty()) {
            arrST = dbManager.getAllStudents()
            Log.d("D/d", "" + arrST.size)
            setAdapter()
        }


        buttonUpdate.setOnClickListener {
            Toast.makeText(this, "ID: " + arrST[position].mID, Toast.LENGTH_SHORT).show()
            arrST[position].mName = editTextName.text.toString()
            arrST[position].mPhone = editTextPhone.text.toString()
            arrST[position].mAddress = editTextAddress.text.toString()
            arrST[position].mEmail = editTextEmail.text.toString()
            arrST[position].mImage = DbBitmapUtils.getBytes(imageView.drawable.toBitmap())
            dbManager.updateStudent(arrST[position])
            resetEditText()
            loadRecyclerView()
        }

        buttonInsert.setOnClickListener {
            dbManager.addStudents(getEditText())
            resetEditText()
            loadRecyclerView()
        }

        buttonDelete.setOnClickListener {
            dbManager.deleteStudent(arrST[position].mID)
            resetEditText()
            loadRecyclerView()
        }

        imageView.setOnClickListener {
            requestStoragePermission()
        }
    }

    private fun loadRecyclerView() {
        arrST.clear()
        arrST.addAll(dbManager.getAllStudents())
        setAdapter()
    }

    private fun resetEditText() {
        editTextName.text = null
        editTextPhone.text = null
        editTextAddress.text = null
        editTextEmail.text = null
        imageView.setImageResource(R.drawable.photo)
    }

    private fun setEditText(student: Student) {
        editTextName.setText(student.mName)
        editTextPhone.setText(student.mPhone)
        editTextAddress.setText(student.mAddress)
        editTextEmail.setText(student.mEmail)
        imageView.setImageBitmap(DbBitmapUtils.getImage(student.mImage))
    }

    private fun getEditText(): Student {
        return Student(
            editTextName.text.toString(),
            editTextPhone.text.toString(),
            editTextAddress.text.toString(),
            editTextEmail.text.toString(),
            DbBitmapUtils.getBytes(imageView.drawable.toBitmap())
        )
    }

    private fun requestStoragePermission() {
        Dexter.withActivity(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    openGallery()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(
                        this@MainActivity,
                        "Check permission stogare",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }).check()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageView.setImageURI(data!!.data)
        }
    }

    private fun setAdapter() {
        if (recyclerviewStudents.adapter == null) {
            recyclerviewStudents.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = StudentsAdapter(arrST)
                addOnItemClickListener(object : OnItemClickListener {
                    override fun onItemClick(position: Int, view: View) {
                        setEditText(arrST[position])
                        this@MainActivity.position = position
                        Toast.makeText(this@MainActivity, "" + position, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        } else {
            recyclerviewStudents.adapter!!.notifyDataSetChanged()
        }
    }
}
