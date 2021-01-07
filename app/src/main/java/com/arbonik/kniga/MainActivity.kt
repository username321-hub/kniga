package com.arbonik.kniga

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
private const val TAG = "QuizViewModel"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0
data class Question(@StringRes val textResId:Int,
                    val answer: Boolean)
class MainActivity : AppCompatActivity() {
    lateinit var True:Button
    lateinit var False:Button
    lateinit var Next:ImageButton
    lateinit var Prev:ImageButton
    lateinit var Ques:TextView
    lateinit var cheat:Button
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }
    override fun onPause() {
        super.onPause()
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG,"onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
    override fun onStop() {
        super.onStop()
    }
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "Got a QuizViewModel:$quizViewModel")
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex
        True = findViewById(R.id.True)
        False = findViewById(R.id.False)
        Next = findViewById(R.id.Next)
        Prev = findViewById(R.id.imageButton2)
        Ques = findViewById(R.id.question)
        cheat = findViewById(R.id.cheat_button)

        Next.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }
        Prev.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }
        True.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            checkAnswer(true)
        }
        False.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            checkAnswer(false)
        }
        Ques.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        cheat.setOnClickListener {view->
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = MainActivity2.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val options = ActivityOptions
                        .makeClipRevealAnimation(view, 0, 0, view.width, view.height)
                startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
            } else {
                startActivityForResult(intent, REQUEST_CODE_CHEAT)
            }

        }
        updateQuestion()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode,
            resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT)
        {
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }
    fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        Ques.setText(questionTextResId)
    }
    fun checkAnswer(userAnswer:Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.corc_toast
            else -> R.string.incr_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_LONG)
            .show()
    }
}
