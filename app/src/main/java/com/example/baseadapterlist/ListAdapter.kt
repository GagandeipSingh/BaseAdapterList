package com.example.baseadapterlist

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import com.example.baseadapterlist.databinding.UpdateDialogBinding

class ListAdapter(private var context:Context, private var list : ArrayList<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(positon: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if(view == null) view = LayoutInflater.from(parent?.context).inflate(R.layout.itemview,parent,false)
        view?.findViewById<TextView>(R.id.textView)?.text = list[positon]

        view?.findViewById<Button>(R.id.delete)?.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setCancelable(false)
            alertDialog.setTitle("Conformation!!")
            alertDialog.setMessage("Do you want to delete this..")
            alertDialog.setPositiveButton("Yes"){_,_ ->
                list.removeAt(positon)
                notifyDataSetChanged()
            }
            alertDialog.setNegativeButton("No"){_,_ ->}
            alertDialog.show()
        }

        view?.findViewById<Button>(R.id.update)?.setOnClickListener {
            val layoutInflater = LayoutInflater.from(context)
            val updateBinding = UpdateDialogBinding.inflate(layoutInflater)
            val updateDialog = Dialog(context)
            updateDialog.setContentView(updateBinding.root)
            updateDialog.show()
            updateBinding.etName.setText(list[positon])
            updateBinding.positiveButton.setOnClickListener {
                if(updateBinding.etName.text.toString().trim().isEmpty()){
                    updateBinding.textInputLayout.error = "Enter Name.."
                }
                else{
                    list[positon] = (updateBinding.etName.text.toString().trim())
                    notifyDataSetChanged()
                    updateDialog.dismiss()
                }
            }
            updateBinding.etName.doOnTextChanged { _, _, _, _ ->
                updateBinding.textInputLayout.isErrorEnabled = false
            }
        }
        return view!!
    }
}