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
import com.example.ioannisfanariotis.databinding.CountrySpinnerBinding
import com.example.ioannisfanariotis.databinding.YearSpinnerBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    var yearFlag = false            //no year selected yet
    var countryFlag = false         //no country selected yet
    var searchYear: String = ""     //the year that is passed to the intent (blank for now)
    var searchCountry: String = ""  //the country that is passed to the intent (blank for now)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.year?.setOnClickListener {
            yearPicker()
        }

        binding?.country?.setOnClickListener {
            countryPicker()
        }

        binding?.search?.setOnClickListener {
            if(!yearFlag){
                Toast.makeText(this, "Please pick a year", Toast.LENGTH_SHORT).show()
            }else if(!countryFlag){
                Toast.makeText(this, "Please pick a country", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, ShowActivity::class.java)
                intent.putExtra("searchYear", searchYear)
                intent.putExtra("searchCountry", searchCountry)
                startActivity(intent) //no finish, so we can go back and repeat it
            }
        }
    }

    private fun yearPicker() {
        val yearDialog = Dialog(this)
        val dialogBinding = YearSpinnerBinding.inflate(layoutInflater)
        yearDialog.setContentView(dialogBinding.root)
        yearDialog.setCanceledOnTouchOutside(false)
        var yearOptions = arrayOf("") //the first position of year_spinner.xml contains nothing
        for (i in 1970..2050){
            val y = i.toString()
            yearOptions = yearOptions.plus(y)
        }
        dialogBinding.option.adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, yearOptions) //setting up the year_spinner.xml
        dialogBinding.option.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(yearOptions[position].isNotEmpty()){
                    yearFlag=true
                    dialogBinding.result.text = yearOptions[position]
                    binding?.year?.text = yearOptions[position] //button's name becomes the selected year, so the user can see what is chosen
                    searchYear = binding?.year?.text.toString()
                    yearDialog.dismiss()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                dialogBinding.result.text = "Please select an option"
            }
        }
        yearDialog.show()
    }

    private fun countryPicker(){
        val countryDialog = Dialog(this)
        val dialogBinding = CountrySpinnerBinding.inflate(layoutInflater)
        countryDialog.setContentView(dialogBinding.root)
        countryDialog.setCanceledOnTouchOutside(false)
        val countryOptions = arrayOf("", "GR", "GB", "ES", "DE", "IT", "FR", "PT", "BE", "CY", "TR", "NL")

        dialogBinding.option.adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, countryOptions) //setting up the country_spinner.xml
        dialogBinding.option.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(countryOptions[position].isNotEmpty()){
                    countryFlag=true
                    dialogBinding.result.text = countryOptions[position]
                    binding?.country?.text = countryOptions[position] //button's name becomes the selected country, so the user can see what is chosen
                    searchCountry = binding?.country?.text.toString()
                    countryDialog.dismiss()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                dialogBinding.result.text = "Please select an option"
            }
        }
        countryDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}