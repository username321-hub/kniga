package com.arbonik.kniga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes

data class Question(@StringRes val textResId:Int,
                    val answer: Boolean)
class MainActivity : AppCompatActivity() {
    lateinit var True:Button
    lateinit var False:Button
    lateinit var Next:ImageButton
    lateinit var Prev:ImageButton
    lateinit var Ques:TextView
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas,true),
        Question(R.string.question_asia,true))
    private var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        True = findViewById(R.id.True)
        False = findViewById(R.id.False)
        Next = findViewById(R.id.Next)
        Prev = findViewById(R.id.imageButton2)
        Ques = findViewById(R.id.question)
        Next.setOnClickListener{
            currentIndex = (currentIndex+1)%questionBank.size
            updateQuestion()
        }
        Prev.setOnClickListener {
            if (currentIndex==0){
                currentIndex=1
            }
            currentIndex = (currentIndex-1)%questionBank.size
            updateQuestion()
        }
        updateQuestion()
        True.setOnClickListener {
            currentIndex = (currentIndex+1)%questionBank.size
            updateQuestion()
            checkAnswer(true)
        }
        False.setOnClickListener {
            currentIndex = (currentIndex+1)%questionBank.size
            updateQuestion()
            checkAnswer(false)
        }
        Ques.setOnClickListener {
            currentIndex = (currentIndex+1)%questionBank.size
            updateQuestion()
        }
    }
    fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        Ques.setText(questionTextResId)
    }
    fun checkAnswer(userAnswer:Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer){
            R.string.corc_toast
        }else{
            R.string.incr_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_LONG)
            .show()
    }
}
