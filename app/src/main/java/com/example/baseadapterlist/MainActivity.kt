package com.example.baseadapterlist

import android.app.Dialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.example.baseadapterlist.databinding.ActivityMainBinding
import com.example.baseadapterlist.databinding.AddDialogBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var arrayList: ArrayList<String>
    private lateinit var listAdapter: ListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        arrayList = arrayListOf("First","Second")
        listAdapter = ListAdapter(this,arrayList)
        binding.listView.adapter = listAdapter

        binding.FAB.setOnClickListener {
            val customBinding = AddDialogBinding.inflate(layoutInflater)
            val addDialog = Dialog(this)
            addDialog.setContentView(customBinding.root)
            addDialog.show()
            customBinding.positiveButton.setOnClickListener {
                if(customBinding.etName.text?.trim().toString().isEmpty()){
                    customBinding.textInputLayout.error = "Enter Name.."
                }
                else{
                    arrayList.add(customBinding.etName.text?.trim().toString())
                    listAdapter.notifyDataSetChanged()
                    addDialog.dismiss()
                }
            }
            customBinding.etName.doOnTextChanged { _, _, _, _ ->
                customBinding.textInputLayout.isErrorEnabled = false
            }
        }
    }
}