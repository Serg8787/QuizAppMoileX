package com.example.quizappmoilex.model

import com.example.quizappmoilex.model.Answer
import com.google.gson.annotations.SerializedName
// conversion with a converter
data class Question(
    @SerializedName("answers")
    val answers: List<Answer>,
    @SerializedName("correctAnswerIndex")
    val correctAnswerIndex: Int,
    @SerializedName("questionText")
    var questionText: String
)