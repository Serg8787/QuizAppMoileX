package com.example.quizappmoilex

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    lateinit var txRightAnswer: TextView
    lateinit var txCountAnswer: TextView
    lateinit var txPercent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        var answer = intent.getIntExtra("currentAnswer", 22)
        var countAnswer = intent.getIntExtra("answer", 23)
        countAnswer = countAnswer + 1

        txPercent = findViewById(R.id.tvPercent)
        txRightAnswer = findViewById(R.id.tvAnswer)
        txCountAnswer = findViewById(R.id.txCountQuestion)
        txRightAnswer.setText(answer.toString())
        txCountAnswer.setText((countAnswer).toString())

        var percent: Double = ((answer * 100) / countAnswer).toDouble()
        txPercent.setText(percent.toString() + "%")


    }

    //    return to start
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }
}