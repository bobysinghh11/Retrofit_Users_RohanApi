package com.stuffshuf.retrofit_users_rohanapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRec.setOnClickListener {



        val retrofit=Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com")
            .build();


        val service=retrofit.create(UsersInterface::class.java)

        service.listRepos().enqueue(retrofitCallback { throwable, response ->

            response?.let {
                if(it.isSuccessful)
                {

                    Log.d("HH", "response ${it.body()}")
                    //  tvViews.text=it.body().toString()

                    // runOnUiThread {

                    //   tvViews.text=it.body().toString()

                    userRec.layoutManager= GridLayoutManager(
                        this,
                        1,
                        GridLayoutManager.VERTICAL,
                        false
                    )

                    userRec.adapter= UsersAdapter(this, it.body()!!)


                    //}
                }
            }
        })


        }
    }
}
