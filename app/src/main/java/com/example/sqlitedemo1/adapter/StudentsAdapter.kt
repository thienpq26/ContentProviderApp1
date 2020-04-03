package com.example.sqlitedemo1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedemo1.DbBitmapUtils
import com.example.sqlitedemo1.R
import com.example.sqlitedemo1.model.Student

class StudentsAdapter(val mList: ArrayList<Student>) :
    RecyclerView.Adapter<StudentsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName = itemView.findViewById<TextView>(R.id.itemTextName)
        val textPhone = itemView.findViewById<TextView>(R.id.itemTextPhone)
        val textAddress = itemView.findViewById<TextView>(R.id.itemTextAddress)
        val textEmail = itemView.findViewById<TextView>(R.id.itemTextEmail)
        val imageStudent = itemView.findViewById<ImageView>(R.id.item_imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_student, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textName.text = mList[position].mName
        holder.textPhone.text = mList[position].mPhone
        holder.textAddress.text = mList[position].mAddress
        holder.textEmail.text = mList[position].mEmail
        holder.imageStudent.setImageBitmap(DbBitmapUtils.getImage(mList[position].mImage))
    }
}
