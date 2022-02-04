package com.example.coroutines

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import okhttp3.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    val callback=object:BroadcastReceiver(){
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceive(context: Context?, intent: Intent?) {
            val manager=getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
             if(manager.activeNetworkInfo!=null){
                 initUrlS()

             }else{
                 Toast.makeText(this@MainActivity,"chimato nel receiver",Toast.LENGTH_LONG).show()
             }
        }

    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUrlS()

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun initrecycler(response: Response) {

        val answer= response.body()!!.string()
        val jsonobject=JSONObject(answer)
        val lista= mutableListOf<String>()
        for(i in 0..jsonobject.getJSONArray("mediaFiles").length()-1){
            val element=jsonobject.getJSONArray("mediaFiles").getJSONObject(i)
            if(element.getString("type").equals( "IMG"))
                lista.add(element.getString("url"))
        }
        recycler=findViewById(R.id.recycler)
    recycler.layoutManager=GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false)
    recycler.adapter=Adapter(lista)



    }
   @RequiresApi(Build.VERSION_CODES.M)
   fun initUrlS(){
       Toast.makeText(this,"chiamato",Toast.LENGTH_LONG).show()
       val handler= CoroutineExceptionHandler { coroutineContext, throwable ->

           runOnUiThread {
               Toast.makeText(this,"offline",Toast.LENGTH_LONG).show()

           }
       }
       CoroutineScope(Dispatchers.IO).launch(handler){
           val job=async {
               val client = OkHttpClient()
               val request = Request.Builder()
               request.url("https://bitbucket.org/!api/2.0/snippets/ettnewmedia/yXLd6n/526f3deac1fcd24c29af03db4114c6565e52c3ee/files/media_json.json").build()
               val rec = request.build()
               client.newCall(rec).execute()
           }.await()
           runOnUiThread {
               Toast.makeText(this@MainActivity,"finito",Toast.LENGTH_LONG).show()
               initrecycler(job)
           }
       }







   }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(callback)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(callback, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

}