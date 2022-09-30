package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    /*val inputStream = resources.openRawResource(R.raw.questions)
    val jsonString = inputStream.bufferedReader().use {
        // the last line of the use function is returned
        it.readText()
    }
    Log.d(TAG, "onCreate: $jsonString")

    val list = listOf("String1", "String2" ... )
    val gson = Gson()
    //val jsonString = gson.toJson(list)
    val sType = object : TypeToken<List<Question>>() { }.type
    val otherList = gson.fromJson<List<Question>>(jsonString, sType)
*/
    lateinit var text: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputStream = resources.openRawResource(R.raw.questions)
        val jsonString = inputStream.bufferedReader().use {
            // the last line of the use function is returned
            it.readText()
        }
        Log.d(TAG, "onCreate: $jsonString")

        /*val list = listOf("String1", "String2" ... )
        val gson = Gson()
        //val jsonString = gson.toJson(list)
        val sType = object : TypeToken<List<Question>>() { }.type
        val otherList = gson.fromJson<List<Question>>(jsonString, sType)*/


        //Log.d(TAG, "otherList: $questions")
        Log.d(TAG, "onCreate: $jsonString")
        Log.d(TAG, "test")

        wireWidgets()
    }

    private fun wireWidgets() {
        text = findViewById(R.id.textView_main_question)
    }
}
