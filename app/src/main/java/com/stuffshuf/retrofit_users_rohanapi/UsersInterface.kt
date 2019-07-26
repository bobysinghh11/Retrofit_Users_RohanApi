package com.stuffshuf.retrofit_users_rohanapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import kotlin.collections.ArrayList

interface UsersInterface {
    @GET("users")

    fun listRepos(): Call<ArrayList<Users>>



    @GET("posts")

    fun useridRepos(@Query("userId")userid:Int): Call<ArrayList<UsersId>>



    @GET("comments")

    fun commentRepos(@Query("postId")commentpost:Int):Call<ArrayList<Comment_model>>


 //   @GET("posts")

  //  fun tryres(@QueryMap map: HashMap<String, String>): Call<ArrayList<UsersId>>


  //  @GET("posts")
  //  fun trypost(@Query("userId") users1:Int,
       //        @Query("userId") users2:Int) : Call<ArrayList<UsersId>>

    //if pass trypost(1, 4) so userId 1 to 4 come out like userId - 1,2,3 and 4
}