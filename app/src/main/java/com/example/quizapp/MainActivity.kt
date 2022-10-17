package com.example.quizapp

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    lateinit var question: TextView
    lateinit var bottomText: TextView
    lateinit var nw: Button
    lateinit var ne: Button
    lateinit var sw: Button
    lateinit var se: Button
    lateinit var image: ImageView
    lateinit var retry: Button

    lateinit var questionList: List<Question>

    val quiz = Quiz(0, 0, 0)

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
            if(nw.text.equals(questionList[quiz.count].correct[0])) {
                quiz.score++
            }
            quiz.count ++
            nextQuestion()
        }
        ne.setOnClickListener {
            if(ne.text.equals(questionList.get(quiz.count).correct[0])) {
                quiz.score++
            }
            else if (questionList.get(quiz.count).correct.size > 1) {
                if(ne.text.equals(questionList.get(quiz.count).correct[1]) || ne.text.equals(questionList.get(quiz.count).correct[2])) {
                    quiz.score++
                }
            }
            quiz.count ++
            nextQuestion()
        }
        sw.setOnClickListener {
            if(sw.text.equals(questionList.get(quiz.count).correct[0])) {
                quiz.score++
            }
            else if (questionList.get(quiz.count).correct.size > 1) {
                if(sw.text.equals(questionList.get(quiz.count).correct[1]) || sw.text.equals(questionList.get(quiz.count).correct[2])) {
                    quiz.score++
                }
            }
            quiz.count ++
            nextQuestion()
        }
        se.setOnClickListener {
            if(se.text.equals(questionList.get(quiz.count).correct[0])) {
                quiz.score++
            }
            else if (questionList.get(quiz.count).correct.size > 1) {
                if(se.text.equals(questionList.get(quiz.count).correct[1]) || se.text.equals(questionList.get(quiz.count).correct[2])) {
                    quiz.score++
                }
            }

            quiz.count ++
            nextQuestion()
        }

        retry.setOnClickListener {
            quiz.score = 0
            quiz.count = 0
            nw.visibility = View.VISIBLE
            ne.visibility = View.VISIBLE
            sw.visibility = View.VISIBLE
            se.visibility = View.VISIBLE
            bottomText.visibility = View.VISIBLE
            retry.visibility = View.GONE
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
        retry = findViewById(R.id.button_end_retry)

        //image.setImageDrawable(drawable)
        image.visibility = View.GONE
        retry.visibility = View.GONE
        val retryText = getString(R.string.retry)
        retry.text = "$retryText"
    }

    private fun nextQuestion() {
        if (quiz.count >= questionList.size) {
            endQuiz()
            return
        }
        var current = questionList.get(quiz.count)
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

        if (quiz.count == 11) {
            image.visibility = View.VISIBLE
        }
        val scoreText = getString(R.string.score)
        val questionText = getString(R.string.question)
        bottomText.text = "$scoreText$quiz.score\n$questionText${quiz.count + 1}/${questionList.size}"
    }

    private fun endQuiz() {
        if (quiz.score > quiz.bestScore) {
            quiz.bestScore = quiz.score
        }
        val gameOverText =  getString(R.string.gameOver)
        val finalScoreText = getString(R.string.finalScore)
        val bestScoreText = getString(R.string.bestScore)
        question.text = "$gameOverText\n$finalScoreText$quiz.score/${questionList.size}\n$bestScoreText$quiz.bestScore"
        if (quiz.score == questionList.size) {
            val congratsText = getString(R.string.congrats)
            question.text = "${question.text}\n$congratsText"
        }
        nw.visibility = View.GONE
        ne.visibility = View.GONE
        sw.visibility = View.GONE
        se.visibility = View.GONE
        bottomText.visibility = View.GONE
        image.visibility = View.GONE
        retry.visibility = View.VISIBLE

    }
}

