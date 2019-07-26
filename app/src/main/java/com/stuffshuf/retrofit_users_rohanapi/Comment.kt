package com.stuffshuf.retrofit_users_rohanapi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.comment_list.*
import kotlinx.android.synthetic.main.comment_list.view.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class Comment: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val commentids=intent.getIntExtra(UsersDetailed.UserDetailedAdapter.viewHolder.COMMENT_POSTID, 1)



        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build();

        val service = retrofit.create(UsersInterface::class.java)
        service.commentRepos(commentids).enqueue(retrofitCallback{ throwable, response ->

             response?.let {

                 if (it.isSuccessful)
                 {

                     userRec.layoutManager=GridLayoutManager(
                         this,
                         1,
                         GridLayoutManager.VERTICAL,
                         false
                     )
                   //  Log.d("comment", "com ${it.body()!!}")
                     Log.d("comment", "id $commentids")
                     val commentadapter=CommentAdapter(it.body()!!)
                     userRec.adapter=commentadapter
                     Log.d("responsecode", "${response.code()}")
                 }

             }
        })



    }
    private class CommentAdapter(val com:ArrayList<Comment_model>):RecyclerView.Adapter<CommentAdapter.CommentViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
           val li=parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val itemView=li.inflate(R.layout.comment_list, parent, false)
            return CommentViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return com.size

        }

        override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
            val comdata=com[position]
            holder.bind(comdata)


        }


        class CommentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

            fun bind(comment_model: Comment_model)
            {
                with(itemView)
                {
                    commentpostId.text=comment_model.postId.toString()
                    commentId.text=comment_model.id.toString()
                    commentName.text=comment_model.name
                    commentEmail.text=comment_model.email
                    commentBody.text=comment_model.body
                }
            }
        }


    }


}
