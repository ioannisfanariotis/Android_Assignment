package com.example.ioannisfanariotis.activities

import android.app.Dialog
import android.widget.AdapterView.OnItemSelectedListener
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.ioannisfanariotis.databinding.ActivityMainBinding
import com.example.ioannisfanariotis.databinding.SpinnerBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    var flag = false //no year selected yet
    var searchYear: String = "" //the year that is passed to the intent (blank for now)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.year?.setOnClickListener {
            yearPicker()
        }

        binding?.search?.setOnClickListener {
            if(!flag){
                Toast.makeText(this, "Please pick a year", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, ShowActivity::class.java)
                intent.putExtra("searchYear", searchYear)
                startActivity(intent) //no finish, so we can go back and repeat it
            }
        }
    }

    private fun yearPicker() {
        val customDialog = Dialog(this)
        val dialogBinding = SpinnerBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        var options = arrayOf("") //the first position of spinner contains nothing
        for (i in 1970..2050){
            val y = i.toString()
            options = options.plus(y)
        }
        dialogBinding.option.adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, options) //setting up the spinner
        dialogBinding.option.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(options[position].isNotEmpty()){
                    flag=true
                    dialogBinding.result.text = options[position]
                    binding?.year?.text = options[position] //button's name becomes the selected year, so the user can see what is chosen
                    searchYear = binding?.year?.text.toString()
                    customDialog.dismiss()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                dialogBinding.result.text = "Please select an option"
            }
        }
        customDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}