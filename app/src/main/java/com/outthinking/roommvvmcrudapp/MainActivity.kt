package com.outthinking.roommvvmcrudapp

import android.health.connect.datatypes.units.Length
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.outthinking.roommvvmcrudapp.databinding.ActivityMainBinding
import com.outthinking.roommvvmcrudapp.db.*

class MainActivity : AppCompatActivity() {
    private lateinit var  binding:ActivityMainBinding
    private lateinit var subscriberViewModel:SubscriberViewModel
    private lateinit var adapter:MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDao
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let{
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }
        })
        initRecyclerView()
    }
    private fun initRecyclerView(){
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter({selectedItem: Subscriber ->listItemClicked(selectedItem)})
        binding.subscriberRecyclerView.adapter = adapter
        displaySubscribersList()
    }
    private fun displaySubscribersList(){
        subscriberViewModel.getSavedSubscribers().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }
    private fun listItemClicked(subscriber: Subscriber){
       subscriberViewModel.initUpdateAndDelete(subscriber)
    }

}