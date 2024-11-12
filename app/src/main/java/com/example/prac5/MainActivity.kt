package com.example.prac5

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.prac5.Database.MainDB
import com.example.prac5.Database.UserDB
import com.example.prac5.Retrofit.User
import com.example.prac5.Retrofit.UserApi
import com.example.prac5.databinding.ActivityMainBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var userApi: UserApi
    @Inject
    lateinit var db: MainDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.main)
        

        db.getDao().getItems().asLiveData().observe(this){list->
            binding.textview1.text=""
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