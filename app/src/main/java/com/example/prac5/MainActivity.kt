package com.example.prac5

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.prac5.Database.MainDB
import com.example.prac5.Database.UserDB
import com.example.prac5.Retrofit.User
import com.example.prac5.Retrofit.UserApi
import com.example.prac5.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)

        val retrofit = Retrofit.Builder().baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val userApi = retrofit.create(UserApi::class.java)

        val db = MainDB.getDB(this)


        db.getDao().getItems().asLiveData().observe(this){list->
            binding.textview1.text=" "
            list.forEach{
                val text = "Id: ${it.id} Name: ${it.firstName} Last Name: ${it.lastName} Maiden name:${it.maidenName} Age: ${it.age}\n"
                binding.textview1.append(text)
            }
        }

        binding.btnAdd.setOnClickListener {
            var id: Int = 1
            for (str in binding.Edit1.text) {
                id = str.code
            }
            CoroutineScope(Dispatchers.IO).launch {
                val user = userApi.getUserById(id)
                val userDb = UserDB(null, user.lastName, user.firstName, user.maidenName, user.age)
                db.getDao().insertItem(userDb)
            }
        }
    }
}