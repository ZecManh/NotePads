package com.example.notepads

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.notepads.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    var fileData = FileData()
    var itemList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemList = fileData.readData(this)
        var arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,itemList)
        binding.lvList.adapter = arrayAdapter
        binding.btnAdd.setOnClickListener {
            var itemName : String = binding.edtNote.text.toString()
            itemList.add(itemName)
            binding.edtNote.setText("")
            fileData.writeData(itemList,applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }
        binding.lvList.setOnItemClickListener { adapterView, view, position, l ->
            var alert = AlertDialog.Builder(this)
            alert.setTitle("Xóa")
            alert.setMessage("Bạn có muốn xóa Item này không?")
            alert.setCancelable(false)
            alert.setNegativeButton("Không", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            alert.setPositiveButton("Có", DialogInterface.OnClickListener { dialogInterface, i ->
                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileData.writeData(itemList,applicationContext)
            })
           alert.create().show()
        }
    }
}