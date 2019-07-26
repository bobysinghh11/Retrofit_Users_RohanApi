package com.stuffshuf.retrofit_users_rohanapi

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.service.autofill.UserData
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_users_detailed.view.*
import okhttp3.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException

class UsersDetailed : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val title = intent.getStringExtra(UsersAdapter.UsersAdapterViewHolder.KEY_USER_ID)
        supportActionBar?.title = title

       val userids =intent.getIntExtra(UsersAdapter.UsersAdapterViewHolder.KEY_ID,1)


        val retrofit=Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()


        val service=retrofit.create(UsersInterface::class.java)

        service.useridRepos(userids).enqueue(retrofitCallback{ throwable, response ->

            response?.let {
                if (it.isSuccessful)


                    userRec.layoutManager=GridLayoutManager(
                        this@UsersDetailed,
                        1,
                        GridLayoutManager.VERTICAL,
                        false)
                    Log.d("users", "users ${it.body()!!}")
                    Log.d("ids", "userids $userids")

                    userRec.adapter=UserDetailedAdapter(it.body()!!)
                }
        })


    }



/*

       userRec.layoutManager=GridLayoutManager(
                              this@UsersDetailed,
                               2,
                               GridLayoutManager.VERTICAL,
                            false)
                    Log.d("users", "users $userslist")

                    userRec.adapter=UserDetailedAdapter(userslist))

}
*/

     class UserDetailedAdapter(val users: ArrayList<UsersId>) :
        RecyclerView.Adapter<UserDetailedAdapter.viewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
            val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val itemView = li.inflate(R.layout.activity_users_detailed, parent, false)
            return viewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return users.size
        }

        override fun onBindViewHolder(holder: viewHolder, position: Int) {
            val usersposition = users[position]
            holder.bind(usersposition)


        }


        class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            companion object{
                val COMMENT_POSTID="ID"
            }




            fun bind(usersId: UsersId) {


                with(itemView)
                {

                    tvid.text = usersId.id.toString()
                    tvuserid.text = usersId.userId.toString()
                    title.text = usersId.title
                    body.text = usersId.body

                    setOnClickListener {
                        val intent=Intent(context, Comment::class.java)
                        intent.putExtra(COMMENT_POSTID, usersId.id)
                        Log.d("fr", "bb ${usersId.id}")
                        context.startActivity(intent)


                    }

                }
            }
        }
    }
}

