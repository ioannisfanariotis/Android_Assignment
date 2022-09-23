package com.example.ioannisfanariotis

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import com.example.ioannisfanariotis.databinding.ActivityShowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowActivity : AppCompatActivity() {

    private var binding: ActivityShowBinding? = null
    private var myAdapter: MyAdapter? = null
    private var myLoader: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        startLoading() //spinner
        setSupportActionBar(binding?.toolbar)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbar?.setNavigationOnClickListener{
            onBackPressed()
        }

        binding?.rvList?.layoutManager = LinearLayoutManager(this@ShowActivity)

        val bundle: Bundle? = intent.extras //receiving user's selected year
        if(bundle?.containsKey("searchYear")!!)
        {
            val searchYear = intent.getStringExtra("searchYear")
            if (searchYear != null) {
                getRequest(searchYear)
                myAdapter = MyAdapter()
                binding?.rvList?.adapter = myAdapter
            }
        }
    }

    private fun getRequest(searchYear: String) {
        val builder = Utils.getInstance().create(APIInterface::class.java) //creates an instance of API interface
        val data = builder.getYear(searchYear)                             //creates a call instance, looking up selected year as a contributor
        data.enqueue(object : Callback<List<RequestItem>?> {               //fetch a list
            override fun onResponse(call: Call<List<RequestItem>?>, response: Response<List<RequestItem>?>) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    myAdapter?.submitList(body)
                    cancelLoading()
                }else{
                    Toast.makeText(this@ShowActivity, "Fail to retrieve data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<RequestItem>?>, t: Throwable) {
                Toast.makeText(this@ShowActivity, "Fail to retrieve data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun startLoading(){
        myLoader = Dialog(this)
        myLoader?.setContentView(R.layout.loading_spinner)
        myLoader?.show()
    }

    private fun cancelLoading(){
        if(myAdapter!=null){
            myLoader?.dismiss()
            myLoader = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}