package com.example.hm7_cleanarchitecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.hm7_cleanarchitecture.fragments.BottomNavFragment


//class MainActivity : AppCompatActivity(R.layout.activity_main)

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, BottomNavFragment())
            .commit()
    }


}


//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//

//    }
//
//}
