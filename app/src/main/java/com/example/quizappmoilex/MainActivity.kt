package com.example.quizappmoilex

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.quizappmoilex.model.Questions
import com.google.gson.GsonBuilder
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    lateinit var txQuestion: TextView
    lateinit var btAnswer0: Button
    lateinit var btAnswer1: Button
    lateinit var btAnswer2: Button
    lateinit var btAnswer3: Button
    lateinit var nextQuestion: Button
    var answerIndex = 0
    var countRightAnswers = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txQuestion = findViewById(R.id.txQuestion)
        btAnswer0 = findViewById(R.id.btAnswer0)
        btAnswer1 = findViewById(R.id.btAnswer1)
        btAnswer2 = findViewById(R.id.btAnswer2)
        btAnswer3 = findViewById(R.id.btAnswer3)
        var buttons = arrayOfNulls<Button>(4)
        buttons.set(0, btAnswer0)
        buttons.set(1, btAnswer1)
        buttons.set(2, btAnswer2)
        buttons.set(3, btAnswer3)
        nextQuestion = findViewById(R.id.btNextQuestion)
        getStrFromJSON(this)
        setQuestion(answerIndex)

        btAnswer0.setOnClickListener {
            val indexClickButton = 0
            receiveAnswer(indexClickButton, buttons = buttons)
        }
        btAnswer1.setOnClickListener {

            val indexClickButton = 1
            receiveAnswer(indexClickButton, buttons = buttons)

        }

        btAnswer2.setOnClickListener {

            val indexClickButton = 2
            receiveAnswer(indexClickButton, buttons = buttons)

        }
        btAnswer3.setOnClickListener {

            val indexClickButton = 3
            receiveAnswer(indexClickButton, buttons = buttons)

        }

        // Next question
        nextQuestion.setOnClickListener {
            btAnswer0.isEnabled = true
            btAnswer1.isEnabled = true
            btAnswer2.isEnabled = true
            btAnswer3.isEnabled = true
            btAnswer0.setBackgroundColor(ContextCompat.getColor(this, R.color.background_basic))
            btAnswer1.setBackgroundColor(ContextCompat.getColor(this, R.color.background_basic))
            btAnswer2.setBackgroundColor(ContextCompat.getColor(this, R.color.background_basic))
            btAnswer3.setBackgroundColor(ContextCompat.getColor(this, R.color.background_basic))
            answerIndex++
            setQuestion(answerIndex)

        }

    }
//    Loading json from file json.assets

    private fun loadDataFromJsonData(context: Context): String? {
        var input: InputStream? = null
        val jsonStr: String;
        try {
            input = context.assets.open("jsonData")
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            jsonStr = String(buffer)
            return jsonStr

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            input?.close()
        }
        return null

    }
//    Function question setting

    private fun setQuestion(answerIndex: Int) {
        val jsonString = loadDataFromJsonData(this)
        val gson = GsonBuilder().create()
        val questions = gson.fromJson(jsonString, Questions::class.java)
        txQuestion.setText(questions.data[answerIndex].questionText)
        btAnswer0.setText(questions.data[answerIndex].answers[0].answerText)
        btAnswer1.setText(questions.data[answerIndex].answers[1].answerText)
        btAnswer2.setText(questions.data[answerIndex].answers[2].answerText)
        btAnswer3.setText(questions.data[answerIndex].answers[3].answerText)
        Log.i("ans", answerIndex.toString())

    }

    // Response function
    private fun receiveAnswer(indexClickButton: Int, buttons: Array<Button?>) {
        val jsonString = loadDataFromJsonData(applicationContext)
        val gson = GsonBuilder().create()
        val questions = gson.fromJson(jsonString, Questions::class.java)
        if (questions.data[answerIndex].correctAnswerIndex == indexClickButton) {
            buttons[questions.data[answerIndex].correctAnswerIndex]?.setBackgroundColor(
                ContextCompat.getColor(this, R.color.teal_700)
            )
            buttons.forEachIndexed { index, element ->
                if (buttons[index] != buttons[questions.data[answerIndex].correctAnswerIndex]) {
                    buttons[index]?.isEnabled = false
                }
            }
            countRightAnswers++
        } else {
            buttons[indexClickButton]?.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            buttons.forEachIndexed { index, element ->
                buttons[index]?.isEnabled = false
            }
        }
        if (answerIndex == 2) {
            nextQuestion.setText(getString(R.string.result))
            nextQuestion.setOnClickListener {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("currentAnswer", countRightAnswers)
                intent.putExtra("answer", answerIndex)
                startActivity(intent)

            }
        }
    }

    private fun getStrFromJSON(context: Context) {
        val jsonString = loadDataFromJsonData(applicationContext)
        val gson = GsonBuilder().create()
    }
}



