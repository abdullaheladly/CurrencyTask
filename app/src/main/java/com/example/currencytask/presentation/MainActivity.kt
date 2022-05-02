package com.example.currencytask.presentation

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.currencytask.R
import com.example.currencytask.util.ApiStatus
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var   viewModel: TestViewModel


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel=ViewModelProvider(this).get(TestViewModel::class.java)
        viewModel.getData().observe(this) {
            when (it.status) {
                ApiStatus.SUCCESS -> {

                    Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
                }
                ApiStatus.ERROR -> {
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()

                }
                ApiStatus.LOADING -> {

                }
            }
        }
    }
}