package com.example.quizapp

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.concurrent.fixedRateTimer

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
    lateinit var question: TextView
    lateinit var bottomText: TextView
    lateinit var nw: Button
    lateinit var ne: Button
    lateinit var sw: Button
    lateinit var se: Button
    lateinit var image: ImageView

    var count = 0
    var score = 0
    var ids: List<String> =
        listOf("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")

    lateinit var questionList: List<Question>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputStream = resources.openRawResource(R.raw.questions)
        val jsonString = inputStream.bufferedReader().use {
            // the last line of the use function is returned
            it.readText()
        }
        Log.d(TAG, "onCreate: $jsonString")

        val gson = Gson()
        val sType = object : TypeToken<List<Question>>() {}.type
        questionList = gson.fromJson<List<Question>>(jsonString, sType)

        Log.d(TAG, "list: $questionList")
        Log.d(TAG, "test")

        wireWidgets()
        nextQuestion()

        nw.setOnClickListener {
            if(nw.text.equals(questionList.get(count).correct[0])) {
                score++
                //Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            }
            else {
                //Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
            }
            count ++
            nextQuestion()
        }
        ne.setOnClickListener {
            if(ne.text.equals(questionList.get(count).correct[0])) {
                score++
                //Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            }
            else if (questionList.get(count).correct.size > 1) {
                if(ne.text.equals(questionList.get(count).correct[1])) {
                    score++
                    //Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                //Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
            }
            count ++
            nextQuestion()
        }
        sw.setOnClickListener {
            if(sw.text.equals(questionList.get(count).correct[0])) {
                score++
                //Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            }
            else if (questionList.get(count).correct.size > 1) {
                if(sw.text.equals(questionList.get(count).correct[2])) {
                    score++
                    //Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                //Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
            }
            count ++
            nextQuestion()
        }
        se.setOnClickListener {
            if(se.text.equals(questionList.get(count).correct[0])) {
                score++
                //Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            }
            else if (questionList.get(count).correct.size > 1) {
                if(se.text.equals(questionList.get(count).correct[3])) {
                    score++
                    //Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                //Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
            }
            count ++
            nextQuestion()
        }
    }

    private fun wireWidgets() {
        question = findViewById(R.id.textView_main_question)
        bottomText = findViewById(R.id.textView_main_QuestionScore)
        nw = findViewById(R.id.button_main_nwAnswer)
        ne = findViewById(R.id.button_main_neAnswer)
        sw = findViewById(R.id.button_main_swAnswer)
        se = findViewById(R.id.button_main_seAnswer)
        image = findViewById(R.id.imageView_main_andWhyHe)

        image.visibility = View.GONE
    }

    private fun nextQuestion() {
        if (count > questionList.size)
            endQuiz()
        var current = questionList.get(count)
        question.text = current.question
        if (current.answers.size == 4) {
            current.answers.shuffle()
            sw.visibility = View.VISIBLE
            se.visibility = View.VISIBLE

            sw.text = current.answers.get(2)
            se.text = current.answers.get(3)
        } else if (current.answers.size < 4) {
            sw.visibility = View.GONE
            se.visibility = View.GONE
        }
        nw.text = current.answers.get(0)
        ne.text = current.answers.get(1)
    bottomText.text = "Score: $score\nQuestion: ${count + 1}/${questionList.size}"
    }

    private fun endQuiz() {
        question.text = "GAME OVER\nFinal Score: $score/${questionList.size}"
    }
}

