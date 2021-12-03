package com.example.coroutines

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val window=ViewCompat.getWindowInsetsController(getWindow()!!.decorView)
       window?.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
      window?.hide(WindowInsetsCompat.Type.systemBars())
        val client = OkHttpClient()
        var request = Request.Builder()
        request.url("https://bitbucket.org/!api/2.0/snippets/ettnewmedia/yXLd6n/526f3deac1fcd24c29af03db4114c6565e52c3ee/files/media_json.json").build()
        var rec = request.build()
        client.newCall(rec).enqueue(object:Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                runOnUiThread{
                    Toast.makeText(applicationContext,"e fallita",Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call?, response: Response?) {
                 val answer=response!!.body()!!.string()
                val jsonobject=JSONObject(answer)
                val lista= mutableListOf<String>()
                for(i in 0..jsonobject.getJSONArray("mediaFiles").length()-1){
                    val element=jsonobject.getJSONArray("mediaFiles").getJSONObject(i)
                    if(element.getString("type").equals( "IMG"))
                    lista.add(element.getString("url"))
                }
                initrecycler(lista)

            }

        })
    }
    private fun initrecycler(lista: MutableList<String>) {
        runOnUiThread {
            recycler=findViewById(R.id.recycler)
            recycler.layoutManager=GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false)
            recycler.adapter=Adapter(lista)
        }

    }


}